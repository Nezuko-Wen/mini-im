package com.wen.open.miniim.common.handler.decode;

import com.wen.open.miniim.common.context.ConfigContextHolder;
import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packentity.ClientInfo;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import com.wen.open.miniim.common.util.ParseUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wen
 * @date 2023/4/13 13:44
 */
@Slf4j
public class BroadDecode extends MessageToMessageDecoder {
    private String localAddress;

    @Override
    protected void decode(ChannelHandlerContext ctx, Object o, List list) {
        DatagramPacket packet = (DatagramPacket) o;
        if (validate(packet)) {
            String ip = ParseUtil.udpIp(packet);
            ByteBuf byteBuf = packet.copy().content();
            GlobalEnvironmentContext.ip(ip);
            list.add(PacketCodeC.INSTANCE.decode(byteBuf));
        }
    }

    private boolean validate(DatagramPacket packet) {
        if (ConfigContextHolder.config().getSkipElse()) {
            return true;
        }
        String ip = String.valueOf(packet.sender()).replace("/", "").split(":")[0];
        if (localAddress == null) {
            Enumeration<NetworkInterface> interfaces;
            try {
                interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            InetAddress address = addresses.nextElement();
                            if (address instanceof Inet4Address) {
                                localAddress = address.getHostAddress();
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
        return !Objects.equals(ip, localAddress);
    }
}
