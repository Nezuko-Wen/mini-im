package com.wen.open.miniim.common.protocol;

import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Wen
 * @date 2023/4/10 23:01
 */
@SpringBootTest
public class SerializerTest {

    @Test
    public void PacketSerializerTest() {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion((byte) 0x01);
        loginRequestPacket.setUserName("张三");
        loginRequestPacket.setPassword("12345ddd*(");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf encode = packetCodeC.encode(loginRequestPacket);


        LoginRequestPacket decode = (LoginRequestPacket) packetCodeC.decode(encode);

        Assert.assertEquals(decode.getUserName(), loginRequestPacket.getUserName());
        Assert.assertEquals(decode.getPassword(), loginRequestPacket.getPassword());

    }
}
