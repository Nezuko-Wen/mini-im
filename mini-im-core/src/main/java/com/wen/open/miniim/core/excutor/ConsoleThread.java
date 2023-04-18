package com.wen.open.miniim.core.excutor;

import com.wen.open.miniim.StartApplication;
import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packentity.ClientInfo;
import com.wen.open.miniim.common.packet.MessagePacket;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Scanner;

/**
 * @author Wen
 * @date 2023/4/18 11:11
 */
@Slf4j
public class ConsoleThread extends Thread{
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                if (this.isInterrupted()) {
                    throw new InterruptedException();
                }
                Scanner sc = new Scanner(System.in);
                String s = sc.nextLine();
                if (s.equals("ls")) {
                    log.info("======================online-list========================");
                    for (Map.Entry<String, ClientInfo> entry : GlobalEnvironmentContext.onlineMap.entrySet()) {
                        log.info("ip:{}", entry.getKey());
                        log.info("hostname:{}", entry.getValue().getHostname());
                    }
                }
                if (s.equals("send -m")) {
                    log.info("发送给谁");
                    String to = sc.nextLine();
                    log.info("请输入内容");
                    String msg = sc.nextLine();
                    MessagePacket messagePacket = new MessagePacket();
                    messagePacket.setMsg(msg);
                    GlobalEnvironmentContext.onlineMap.get(to).getChannel().writeAndFlush(messagePacket);
                }
                if (s.equals("exit")) {
                    StartApplication.close();
                }
            }
        } catch (InterruptedException e) {
            log.info("im已成功停止");
            System.exit(0);
        }
    }
}
