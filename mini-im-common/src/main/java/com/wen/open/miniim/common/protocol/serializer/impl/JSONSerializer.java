package com.wen.open.miniim.common.protocol.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.wen.open.miniim.common.protocol.serializer.Serializer;
import com.wen.open.miniim.common.protocol.serializer.SerializerAlogrithm;

/**
 * @author Wen
 * @date 2023/4/10 22:19
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON.getVal();
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
