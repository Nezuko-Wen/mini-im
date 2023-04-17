package com.wen.open.miniim.common.packet;

import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/17 18:26
 */
@Data
public class ClosePacket extends Packet {
    private String ip;

    @Override
    public Byte getCommand() {
        return Command.CLOSE.getVal();
    }
}
