package com.ruoyi.framework.config;
import com.ruoyi.framework.service.ISocketUdpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

@Component
//@WebListener
public class SocketUdpListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(SocketUdpListener.class);

    @Value("${socket.udp.port}")
    private int udpPort;
    @Value("${socket.udp.max_data_size}")
    private int maxUdpDataSize;
    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

    @Resource(name ="realTimeDataDisplay")
    private ISocketUdpService socketUdpService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("========> 启动一个线程，监听UDP数据包，监听端口 {} <======== ", udpPort);
        // 启动一个线程，监听UDP数据报
        threadPoolConfig.threadPoolTaskExecutor().execute(() -> {
            try {
                execteUdpMsg(udpPort);
            } catch (SocketException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private void execteUdpMsg(int port) throws SocketException {

        //创建服务器端DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(port);

        logger.info("=======创建数据报，用于接收客户端发送的数据======");
        while (true) {
            byte[] buffer = new byte[maxUdpDataSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
//                logger.info("=======端口 {} 等待接收消息 ======", udpPort);
                socket.receive(packet);
                // 接收到的UDP信息，然后解码
                buffer = packet.getData();
                String srt2 = new String(buffer, StandardCharsets.UTF_8).trim();
//                logger.info("=======端口 {} 接收接收消息 {} ======", srt2);
                socketUdpService.udpMsgHandle(srt2);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("========> 关闭监听UDP数据包，监听端口 {} <======== ", udpPort);
    }

}
