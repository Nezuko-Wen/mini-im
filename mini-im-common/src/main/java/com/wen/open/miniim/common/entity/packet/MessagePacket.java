package com.wen.open.miniim.common.entity.packet;

import com.wen.open.miniim.common.entity.type.Command;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/13 17:04
 */
@Data
public class MessagePacket extends Packet{

    private String msg;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE.getVal();
    }
}
