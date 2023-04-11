package com.wen.open.miniim.common.protocol;

import com.wen.open.miniim.common.protocol.serializer.Serializer;
import com.wen.open.miniim.common.protocol.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wen
 * @date 2023/4/10 22:32
 */
public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    //代表是mini-im制定的协议
    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST.getVal(), LoginRequestPacket.class);
        packetTypeMap.put(Command.BROADCAST.getVal(), BroadcastPacket.class);

        serializerMap = new HashMap<>();
        Serializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializerAlogrithm(), jsonSerializer);
    }

    /**
     * 协议标识:4字节 - 协议版本:1字节 - 协议序列化算法:1字节 - 协议指令:1字节 - 数据包长度:4字节 - 数据包内容
     */

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] data = Serializer.DEFAULT.serialize(packet);

        //编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        //先跳过协议标识
        byteBuf.skipBytes(4);
        //跳过协议版本
        byteBuf.skipBytes(1);
        //获取序列化算法
        byte serializerAlogrithm = byteBuf.readByte();
        //数据包指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        return serializerMap.get(serializerAlogrithm).deserialize(packetTypeMap.get(command), data);
    }


}
