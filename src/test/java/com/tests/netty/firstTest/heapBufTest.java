package com.tests.netty.firstTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.CharsetUtil;
import org.junit.Test;


public class heapBufTest {
    @Test
    public void testHeapBuffer2() {
        //取得堆内存 (但是默认是 directByDefault=true)
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.buffer();
        heapBuf.writeBytes("中国万岁！".getBytes(CharsetUtil.UTF_8));
        if (heapBuf.hasArray()) {
            //取得内部数组
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
           // Logger.info("---------chen------------>" + new String(array, offset, length, UTF_8));
        }
        System.out.println("heapBuf.hasArray(): " + heapBuf.hasArray());
        heapBuf.release();

    }
}
