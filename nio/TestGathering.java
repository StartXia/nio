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
 * 将多个buffer的数据写入到同一个管道
 * @author  夏丽勇
 * @version $Id: TestGathering.java, v 0.1 2018年10月16日 下午2:27:32 夏丽勇 Exp $
 */
public class TestGathering {
    
    public static void main(String[] args) throws IOException {
        RandomAccessFile afile = new RandomAccessFile("C:\\Users\\夏丽勇\\Desktop\\testProject\\test.txt", "rw");
        FileChannel fileChannel = afile.getChannel();
        ByteBuffer head = ByteBuffer.allocate(13);
        String a = "我是谁啊,";
        System.out.println(a.getBytes().length);
        head.put(a.getBytes());
        // 变读为写
        head.flip();
        ByteBuffer body = ByteBuffer.allocate(13);
        String b = " 你是猪啊";
        body.put(b.getBytes());
        body.flip();
        ByteBuffer[] byteArray = {head, body};
        
        long writeLen = fileChannel.write(byteArray);
        System.out.println(writeLen);
        afile.close();
    }
}
