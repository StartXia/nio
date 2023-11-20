/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * TCP 服务端
 * @author  夏丽勇
 * @version $Id: TcpServer.java, v 0.1 2018年10月17日 下午5:12:51 夏丽勇 Exp $
 */
public class TcpServer {
    
    // 缓冲区大小
    private static final int BUFFER_SIZE = 1024;
    
    // 超时时间，单位ms
    private static final int TIME_OUT = 3000;
    
    // 本地监听端口
    private static final int LISTEN_PORT = 1222;
    
    public static void main(String[] args) {

        try {
            // 创建选择器
            Selector selector = Selector.open();
            // 打开监听信道
            ServerSocketChannel listenerChannel = ServerSocketChannel.open();
            // 端口绑定
            listenerChannel.socket().bind(new InetSocketAddress(LISTEN_PORT));
            // 设置为非阻塞模式
            listenerChannel.configureBlocking(false);
            // 将选择器绑定到监听信道
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 创建一个处理协议的实现类，由它来具体操作
            TCPProtocol protocol = new TCPProtocolImpl(BUFFER_SIZE);
            while (true) {
                // 等待某信道就绪（或超时）
                if (selector.select(TIME_OUT) == 0) {
                    System.out.println("服务端：等待你来撩。。。");
                    // 等待超时
                    continue;
                }
                // 包含了每个准备好某一I/O操作的信道的SelectionKey
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    if (key.isAcceptable()) {
                        // 有客户端连接请求
                        protocol.handleAccept(key);
                    }
                    if (key.isReadable()) {
                        // 从客户端读取数据
                        protocol.handleRead(key);
                    }
                    if (key.isValid() && key.isWritable()) {
                        // 客户端可写
                        protocol.handleWrite(key);
                    }
                    // 移除处理过的键
                    keys.remove();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
