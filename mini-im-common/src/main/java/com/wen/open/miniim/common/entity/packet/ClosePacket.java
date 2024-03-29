package com.wen.open.miniim.common.entity.packet;

import com.wen.open.miniim.common.entity.type.Command;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/17 18:26
 */
@Data
public class ClosePacket extends Packet {
    private String msg;

    @Override
    public Byte getCommand() {
        return Command.CLOSE.getVal();
    }
}
