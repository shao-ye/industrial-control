package com.ruoyi.industry.socket;

import com.ruoyi.common.utils.Threads;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.domain.server.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final String SERVER_HOSTNAME = "192.168.252.155";
    // 服务器端口
    private static final int SERVER_PORT = 9016;
    // 本地发送端口
    private static final int LOCAL_PORT = 9010;

    public static void main(String[] args) {

        try {
            // 1，创建udp服务。通过DatagramSocket对象。
            DatagramSocket socket = new DatagramSocket(LOCAL_PORT);
            // 2，确定数据，并封装成数据包。DatagramPacket(byte[] buf, int length, InetAddress
            // address, int port)
            byte[] buf = "你好，世界".getBytes();
            System.out.println(buf.length);
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(SERVER_HOSTNAME),
                    SERVER_PORT);
            // 3，通过socket服务，将已有的数据包发送出去。通过send方法。
            socket.send(dp);
            // 4，关闭资源。
            socket.close();
            /*while (true) {
                byte[] buffer = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
//                    logger.info("=======端口 {} 等待接收消息 ======", SERVER_PORT);
                    socket.receive(packet);
                    // 接收到的UDP信息，然后解码
                    buffer = packet.getData();
                    String srt2 = new String(buffer, StandardCharsets.UTF_8).trim();
                    logger.info("=======端口 {} 接收接收消息 {} ======", srt2);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
