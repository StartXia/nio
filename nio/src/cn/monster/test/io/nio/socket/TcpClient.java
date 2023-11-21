/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * TCP 客户端
 * @author  夏丽勇
 * @version $Id: TcpClient.java, v 0.1 2018年10月17日 下午3:43:03 夏丽勇 Exp $
 */
public class TcpClient {
    
    // 信道选择器
    private Selector selector;
    
    // 与服务器通信的信道
    SocketChannel socketChannel;
    
    // 要连接的服务器IP地址
    private String serverIp;
    
    // 远程服务器监听端口
    private int serverPort;
    
    public TcpClient(String serverIp, int serverPort) throws IOException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        initialize();
    }

    /**
     * @throws IOException 
     * 
     */
    private void initialize() throws IOException {
        // 打开套接字管道并连接到远程地址
        socketChannel = SocketChannel.open(new InetSocketAddress(serverIp, serverPort));
        // 设置为非阻塞模式
        socketChannel.configureBlocking(false);
        
        // 创建选择器
        selector = Selector.open();
        // 将套接字通道注册到选择器
        socketChannel.register(selector, SelectionKey.OP_READ);
        
        
        // 启动读取线程
        new TcpClientReadThread(selector);
    }
    
    public void sendMsg(String message) throws IOException {
        // 将byte数组包装到缓冲区中
        ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes());
        // 将字节序列从给定的缓冲区中写入此通道
        socketChannel.write(writeBuffer);
    }
    
    public static void main(String[] args) throws IOException {
        // 本地IP，I Can
        TcpClient client = new TcpClient("127.0.0.1", 1222);
        client.sendMsg("hi, 你是猪吗？");
    }
}
