package com.github.lan1tian.eslearn.cluster.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class MulticastHost {

    private String ip;
    private int port;
    private int id;
    private MulticastSocket sock;

    public MulticastHost(int id, String ip, int port) {
        try {
            this.id = id;
            this.ip=ip;
            this.port=port;
            this.sock = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName(ip);
            sock.joinGroup(address);
            //允许发送方收到多播包
            sock.setLoopbackMode(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 先发送消息再读取消息
     */
    public void start() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                byte[] buf = (id + ":" + scan.nextLine()).getBytes();
                DatagramPacket outpack = new DatagramPacket(buf, 0, buf.length, InetAddress.getByName(ip), port);
                outpack.setData(buf);
                sock.send(outpack);
                DatagramPacket in = new DatagramPacket(new byte[1024], 1024);
                sock.receive(in);
                System.out.println("组播消息：" + new String(in.getData(), 0, in.getLength()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close() {
        try {
            sock.leaveGroup(InetAddress.getByName(ip));
            sock.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
