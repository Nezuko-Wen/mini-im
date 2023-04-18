package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.ConfigContextHolder;
import com.wen.open.miniim.common.entity.FileBase;
import com.wen.open.miniim.common.entity.packet.FilePacket;
import com.wen.open.miniim.common.entity.type.FileStatus;
import com.wen.open.miniim.common.entity.type.TransferType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author Wen
 * @date 2023/4/18 13:45
 */
@Slf4j
public class FileHandler extends CommandHandler<FilePacket>{
    @Override
    void doHandler(ChannelHandlerContext ctx, FilePacket packet) {
        FilePacket response = new FilePacket();
        FileBase fileBase = packet.getBase();
        response.setBase(fileBase);
        switch (packet.getType()) {
            case REQUEST:
                response.setType(TransferType.INSTRUCT);
                ctx.writeAndFlush(response);
                break;
            case INSTRUCT://服务端通知客户端传输
                try {
                    if (fileBase.getStatus() == FileStatus.COMPLETE) {
                        //已传输完成
                        return;
                    }
                    response.setType(TransferType.DATA);
                    Integer readPosition = fileBase.getReadPosition();
                    File file = new File(fileBase.getFileUrl());
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");//r: 只读模式 rw:读写模式
                    randomAccessFile.seek(fileBase.getReadPosition());
                    byte[] bytes = new byte[ConfigContextHolder.config().getFileSplitLength()];
                    int readSize = randomAccessFile.read(bytes);
                    if (readSize <= 0) {//文件已读完
                        randomAccessFile.close();
                        fileBase.setStatus(FileStatus.COMPLETE);
                    }
                    fileBase.setBeginPos(readPosition);
                    fileBase.setEndPos(readPosition + readSize);
                    //不足1024需要拷贝去掉空字节
                    if (readSize < ConfigContextHolder.config().getFileSplitLength()) {
                        byte[] copy = new byte[readSize];
                        System.arraycopy(bytes, 0, copy, 0, readSize);
                        fileBase.setBytes(copy);
                        fileBase.setStatus(FileStatus.END);
                    } else {
                        fileBase.setBytes(bytes);
                        fileBase.setStatus(FileStatus.CENTER);
                    }
                    randomAccessFile.close();
                    ctx.writeAndFlush(response);
                } catch (Exception e) {
                    log.warn("文件传输异常", e);
                }
                break;
            case DATA://服务端收到客户端文件数据
                try {
                    response.setType(TransferType.INSTRUCT);
                    if (FileStatus.COMPLETE == fileBase.getStatus()) {
                        return; //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
                    }
                    File file = new File("file/" + fileBase.getFileName());
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");//r: 只读模式 rw:读写模式
                    randomAccessFile.seek(fileBase.getBeginPos());      //移动文件记录指针的位置,
                    randomAccessFile.write(fileBase.getBytes());        //调用了seek（start）方法，是指把文件的记录指针定位到start
                    // 字节的位置。也就是说程序将从start字节开始写数据
                    randomAccessFile.close();
                    if (FileStatus.END == fileBase.getStatus()) {
                        fileBase.setStatus(FileStatus.COMPLETE);
                    }
                    //文件分片传输指令
                    fileBase.setReadPosition(fileBase.getEndPos() + 1);    //读取位置
                    ctx.writeAndFlush(response);
                } catch (Exception e) {
                    log.warn("文件下载异常", e);
                }
                break;
            default:
                break;
        }
    }
}
