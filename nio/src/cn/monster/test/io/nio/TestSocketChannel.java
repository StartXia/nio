/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 
 * @author  夏丽勇
 * @version $Id: TestSocketChannel.java, v 0.1 2018年10月17日 下午1:57:41 夏丽勇 Exp $
 */
public class TestSocketChannel {
    
    public static void main(String[] args) {
        try {
            // 打开套接字通道
            SocketChannel socketChannel = SocketChannel.open();
            // 连接此通道套接字，如果已建立连接，则返回true
            boolean isConnect = socketChannel.connect(new InetSocketAddress("192.168.32.122", 80));
            System.out.println("是否连接成功：" + isConnect);
            // 从SocketChannel读取数据到Buffer
            ByteBuffer buf = ByteBuffer.allocate(48);
            // 返回值表示读了多少字节进Buffer里，如果返回-1，表示已经读到了流的末尾（连接关闭）
            int bytesRead = socketChannel.read(buf);
            socketChannel.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
