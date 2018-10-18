/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * @author  夏丽勇
 * @version $Id: TestFileChannel.java, v 0.1 2018年10月8日 下午4:10:22 夏丽勇 Exp $
 */
public class TestFileChannel {
    
    public static void main(String[] args) {
        try {
            // 创建从中读取和向其中写入的随机访问文件流
            RandomAccessFile aFile = new RandomAccessFile("C:\\Users\\夏丽勇\\Desktop\\testProject\\FileChannel.txt", "rw");
            // FileChannel: 用于读取、写入、映射和操作文件的通道。 getChannel() : 返回与此文件关联的唯一FileChannel对象
            FileChannel fileChannel = aFile.getChannel();
            // 分配一个新的字节缓冲区
            ByteBuffer buf = ByteBuffer.allocate(48);
            // 将字节序列从此管道读入给定的缓冲区。 读取的字节数，可能为零，如果该通道已到达流的末尾，则返回 -1
            int bytesRead = fileChannel.read(buf);
            System.out.println("bytesRead:" + bytesRead);
            while (bytesRead != -1) {
                System.out.println("存在数据,bytesRead:" + bytesRead);
                // 反转此缓冲区、将限制设置为当前位置
                buf.flip();
                while (buf.hasRemaining()) {// 告知在当前位置和限制之间是否有元素
                    System.out.print("char:" + (char)buf.get());
                    System.out.print("\t");
                    System.out.println("string:" + String.valueOf(buf.get()));
                }
                // 清除缓冲区、将位置设置为0，将限制设置为容量，并丢弃标记
                buf.clear();
                bytesRead = fileChannel.read(buf);
            }
            aFile.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
