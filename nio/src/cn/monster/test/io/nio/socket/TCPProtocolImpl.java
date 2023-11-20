/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import com.yunrich.monster.common.utils.convert.DateUtils;

/**
 * 
 * @author  夏丽勇
 * @version $Id: TCPProtocolImpl.java, v 0.1 2018年10月17日 下午6:02:59 夏丽勇 Exp $
 */
public class TCPProtocolImpl implements TCPProtocol{
    
    private int bufferSize;
    
    public TCPProtocolImpl(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /** 
     * @see cn.monster.test.io.nio.socket.TCPProtocol#handleAccept(java.nio.channels.SelectionKey)
     */
    @Override
    public void handleAccept(SelectionKey selectionKey) throws IOException {
        System.out.println("有客户端请求来撩。。。");
        SocketChannel clientChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
    }

    /** 
     * @see cn.monster.test.io.nio.socket.TCPProtocol#handleRead(java.nio.channels.SelectionKey)
     */
    @Override
    public void handleRead(SelectionKey selectionKey) throws IOException {
        System.out.println("开始读取客户端来撩数据。。。");
        // 获得与客户端通信的信道
        SocketChannel clientChannel = (SocketChannel)selectionKey.channel();
        
        // 得到并清空缓冲区
        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
        byteBuffer.clear();
        
        int bytesRead = clientChannel.read(byteBuffer);
        if (bytesRead == -1) {
            clientChannel.close();
        } else {
            byteBuffer.flip();
            String receivedString = Charset.forName("utf-8").newDecoder().decode(byteBuffer).toString();
            System.out.println("服务端：接收到来自：" + clientChannel.socket().getRemoteSocketAddress() + "的信息：" + receivedString);
            // 准备发送的文本
            String sendString="你好,客户端. @"+ DateUtils.transfer2LongDateDtPart(DateUtils.getCurrentDate()) + " " + DateUtils.transfer2LongDateTmPart(DateUtils.getCurrentTime()) +"，已经收到你的信息【"+receivedString+"】";
            byteBuffer.clear();
            byteBuffer=ByteBuffer.wrap(sendString.getBytes());
            clientChannel.write(byteBuffer);
            
            // 设置为下一次读取或是写入做准备
            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    /** 
     * @see cn.monster.test.io.nio.socket.TCPProtocol#handleWrite(java.nio.channels.SelectionKey)
     */
    @Override
    public void handleWrite(SelectionKey selectionKey) throws IOException {
        // TODO Auto-generated method stub
        
    }

}
