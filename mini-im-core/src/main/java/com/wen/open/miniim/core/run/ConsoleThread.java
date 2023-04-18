package com.wen.open.miniim.core.run;

import com.wen.open.miniim.StartApplication;
import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.entity.FileBase;
import com.wen.open.miniim.common.entity.packet.FilePacket;
import com.wen.open.miniim.common.entity.packet.MessagePacket;
import com.wen.open.miniim.common.entity.type.TransferType;
import com.wen.open.miniim.common.packentity.ClientInfo;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Wen
 * @date 2023/4/18 11:11
 */
@Slf4j
public class ConsoleThread extends Thread{
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                if (this.isInterrupted()) {
                    throw new InterruptedException();
                }
                Scanner sc = new Scanner(System.in);
                String s = sc.nextLine();
                if (s.equals("ls")) {
                    log.info("======================online-list========================");
                    for (Map.Entry<String, ClientInfo> entry : GlobalEnvironmentContext.onlineMap.entrySet()) {
                        log.info("ip:{}", entry.getKey());
                        log.info("hostname:{}", entry.getValue().getHostname());
                    }
                }
                if (s.equals("send -m")) {
                    log.info("发送给谁");
                    String to = sc.nextLine();
                    log.info("请输入内容");
                    String msg = sc.nextLine();
                    MessagePacket messagePacket = new MessagePacket();
                    messagePacket.setMsg(msg);
                    GlobalEnvironmentContext.onlineMap.get(to).getChannel().writeAndFlush(messagePacket);
                }
                if (s.equals("exit")) {
                    StartApplication.close();
                }
                if (s.equals("send -f")) {
                    log.info("发送给谁");
                    String to = sc.nextLine();
                    log.info("文件全路径");
                    String fileUrl = sc.nextLine();
                    NioSocketChannel channel = GlobalEnvironmentContext.onlineMap.get(to).getChannel();
                    File file = new File(fileUrl);
                    FilePacket filePacket = new FilePacket();
                    filePacket.setType(TransferType.REQUEST);
                    FileBase fileBase = new FileBase();
                    fileBase.setFileUrl(fileUrl);
                    fileBase.setFileName(file.getName());
                    fileBase.setLength(file.length());
                    fileBase.setReadPosition(0);
                    filePacket.setBase(fileBase);
                    channel.writeAndFlush(filePacket);
                }
            }
        } catch (InterruptedException e) {
            log.info("im已成功停止");
            System.exit(0);
        }
    }
}
