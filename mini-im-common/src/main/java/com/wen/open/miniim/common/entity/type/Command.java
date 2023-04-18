package com.wen.open.miniim.common.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wen
 * @date 2023/4/10 22:08
 */
@Getter
@AllArgsConstructor
public enum Command {

    ONLINE((byte) 0x01),
    BROADCAST((byte) 0x02),
    MESSAGE((byte) 0x03),
    FILE((byte) 0x04),
    CLOSE((byte) 0x05),
    ;

    private final Byte val;

}
