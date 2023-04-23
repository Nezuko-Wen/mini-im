package com.wen.open.miniim.common.entity.packet;

import com.wen.open.miniim.common.entity.type.Command;

/**
 * @author Wen
 * @date 2023/4/23 08:56
 */
public class HeartPacket extends Packet{
    @Override
    public Byte getCommand() {
        return Command.HEART.getVal();
    }
}
