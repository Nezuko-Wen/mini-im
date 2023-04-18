package com.wen.open.miniim.common.entity.packet;

import com.wen.open.miniim.common.entity.type.Command;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/10 22:26
 */
@Data
public class OnlinePacket extends Packet {

    private String online;

    @Override
    public Byte getCommand() {
        return Command.ONLINE.getVal();
    }
}
