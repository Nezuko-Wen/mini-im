package com.wen.open.miniim.common.protocol;

import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/11 11:06
 */
@Data
public class BroadcastPacket extends Packet{

    private String hostname;

    @Override
    public Byte getCommand() {
        return Command.BROADCAST.getVal();
    }
}