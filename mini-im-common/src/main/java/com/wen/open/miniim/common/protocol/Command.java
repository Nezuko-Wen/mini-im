package com.wen.open.miniim.common.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wen
 * @date 2023/4/10 22:08
 */
@Getter
@AllArgsConstructor
public enum Command {

    LOGIN_REQUEST((byte) 0x01);

    private final Byte val;

}
