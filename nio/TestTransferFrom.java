/**
 * 
 * 上海云之富金融信息服务有限公司
 * Copyright (c) 2014-2018 YunCF,Inc.All Rights Reserved.
 */
package cn.monster.test.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 管道传输：管道之间的数据传输
 * @author  夏丽勇
 * @version $Id: TestTransferFrom.java, v 0.1 2018年10月16日 下午3:38:34 夏丽勇 Exp $
 */
public class TestTransferFrom {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        // 源管道
        RandomAccessFile sourceFile = new RandomAccessFile("C:\\Users\\夏丽勇\\Desktop\\testProject\\nio\\sourceChannel.txt", "rw");
        FileChannel sourceChannel = sourceFile.getChannel();
        System.out.println("source size:" + sourceChannel.size());
        // 目标管道
        RandomAccessFile desFile = new RandomAccessFile("C:\\Users\\夏丽勇\\Desktop\\testProject\\nio\\desChannel.txt", "rw");
        FileChannel desChannel = desFile.getChannel();
        System.out.println("des size:" + desChannel.size());
        long writeLen = desChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        System.out.println("writeLen:" + writeLen);
        desChannel.close();
        sourceChannel.close();
    }
}
