package com.wen.open.miniim.common.protocol.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wen
 * @date 2023/4/10 22:24
 */
@Getter
@AllArgsConstructor
public enum SerializerAlogrithm {
    JSON((byte) 0x01);

    private final Byte val;
}
