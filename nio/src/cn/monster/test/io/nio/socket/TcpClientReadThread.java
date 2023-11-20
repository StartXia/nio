/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 客户端读取线程代码
 * @author  夏丽勇
 * @version $Id: TcpClientReadThread.java, v 0.1 2018年10月17日 下午4:10:42 夏丽勇 Exp $
 */
public class TcpClientReadThread implements Runnable {
    
    private Selector selector;
    
    public TcpClientReadThread(Selector selector) {
        this.selector = selector;
        new Thread(this).start();
    }

    /** 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            System.out.println("执行线程。。。");
            while(selector.select() > 0) {
                System.out.println("有可用的管道。。。");
                // 遍历每个有可用的IO操作Channel对应的selectionKey
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        buffer.flip();
                        // 将字节转换为对应编码的字符串
                        String receivedString = Charset.forName("utf-8").newDecoder().decode(buffer).toString();
                        System.out.println("客户端：接收到来自服务器" + socketChannel.socket().getRemoteSocketAddress() + "的信息" + receivedString);
                        // 为下次读取做准备
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                    //删除正在处理的 SelectionKey
                    selector.selectedKeys().remove(selectionKey);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
