package com.wen.open.miniim.common.entity;

import com.wen.open.miniim.common.entity.type.FileStatus;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/18 16:32
 */
@Data
public class FileBase {
    private String fileUrl;     //客户端文件地址
    private String fileName;    //文件名称
    private Integer beginPos;   //开始位置
    private Integer endPos;     //结束位置
    private byte[] bytes;       //文件字节；再实际应用中可以使用非对称加密，以保证传输信息安全
    private FileStatus status;     //Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
    private Long length;
    private Integer readPosition;
}
