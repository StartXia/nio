/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *   分散：从管道读取数据分散到多个缓冲区
 * @author  夏丽勇
 * @version $Id: TestScatter.java, v 0.1 2018年10月12日 下午2:41:55 夏丽勇 Exp $
 */
public class TestScatter {
    
    public static void main(String[] args) {
        try {
            RandomAccessFile afile = new RandomAccessFile("C:\\Users\\夏丽勇\\Desktop\\testProject\\FileChannel.txt", "rw");
            FileChannel fileChannel = afile.getChannel();
            ByteBuffer header = ByteBuffer.allocate(8);
            ByteBuffer body = ByteBuffer.allocate(8);
            ByteBuffer[] bufferArray = {header, body};
            long bytesRead = fileChannel.read(bufferArray);
            while (bytesRead != -1) {
                header.flip();
                while(header.hasRemaining()) {
                    System.out.println("header + char:" + (char)header.get());
                }
                header.clear();
                body.flip();
                while(body.hasRemaining()) {
                    System.out.println("body + char:" + (char)body.get());
                }
                body.clear();
                bytesRead = fileChannel.read(bufferArray);
            }
            afile.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
