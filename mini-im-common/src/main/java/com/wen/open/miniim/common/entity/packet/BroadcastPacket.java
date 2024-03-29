package com.wen.open.miniim.common.entity.packet;

import com.wen.open.miniim.common.entity.type.Command;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/11 11:06
 */
@Data
public class BroadcastPacket extends Packet{

    private String hostname;

    private Integer serverPort;

    @Override
    public Byte getCommand() {
        return Command.BROADCAST.getVal();
    }
}
