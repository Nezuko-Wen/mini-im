package com.wen.open.miniim.common.entity.packet;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/10 18:50
 */
@Data
public abstract class Packet {
    @JSONField(deserialize = false)
    private Byte version = 0x01;

    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
