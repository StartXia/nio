/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio.socket;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 
 * @author  夏丽勇
 * @version $Id: TCPProtocol.java, v 0.1 2018年10月17日 下午5:53:03 夏丽勇 Exp $
 */
public interface TCPProtocol {
    
    /**
     * 接收一个SocketChannel的处理
     * @param selectionKey
     * @throws IOException
     */
    void handleAccept(SelectionKey selectionKey) throws IOException;
    
    /**
     * 从一个SocketChannel读取信息的处理
     * @param selectionKey
     * @throws IOException
     */
    void handleRead(SelectionKey selectionKey) throws IOException;
    
    
    /**
     * 向一个SokcetChannel写入信息的处理
     * @param selectionKey
     * @throws IOException
     */
    void handleWrite(SelectionKey selectionKey) throws IOException;
}
