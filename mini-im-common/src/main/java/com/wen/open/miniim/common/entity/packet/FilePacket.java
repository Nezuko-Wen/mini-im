package com.wen.open.miniim.common.entity.packet;

import com.wen.open.miniim.common.entity.FileBase;
import com.wen.open.miniim.common.entity.type.Command;
import com.wen.open.miniim.common.entity.type.TransferType;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/18 13:41
 */
@Data
public class FilePacket extends Packet{
    private TransferType type;

    private FileBase base;
    @Override
    public Byte getCommand() {
        return Command.FILE.getVal();
    }
}
