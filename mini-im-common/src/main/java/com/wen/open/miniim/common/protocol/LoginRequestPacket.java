package com.wen.open.miniim.common.protocol;

import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/10 22:26
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userName;

    private String password;

    @Override
    Byte getCommand() {
        return Command.LOGIN_REQUEST.getVal();
    }
}
