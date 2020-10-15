package com.kkagr.netty.thirdExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import java.net.InetAddress;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

   // private Log logger = LogFactory.getLog(NettyServerHandler.class);

    //收到数据时调用
    @Override
    public  void channelRead(ChannelHandlerContext ctx, Object  msg) throws Exception {
        try {
            ByteBuf in = (ByteBuf)msg;
            int readableBytes = in.readableBytes();
            byte[] bytes =new byte[readableBytes];
            in.readBytes(bytes);
            String sss = ByteBufUtil.hexDump(bytes);
           // String sss= byteArrToHex(bytes);
            String sss0= in.toString();
           // System.out.println(new String(bytes));
            System.out.println(sss);
            //System.out.print(in.toString(CharsetUtil.UTF_8));
            String rmsg="66666";//返回的信息
            ByteBuf sliced =in.slice(1,10);

            ByteBuf in2= Unpooled.copiedBuffer(bytes);//进行处理
            ctx.writeAndFlush(in2).sync();

         //   logger.error("服务端接受的消息 : " + msg);
        }catch (Exception e){
            e.printStackTrace();
           // System.out.println(e.toString());
        }
        finally {
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
    /*
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
       // logger.error("连接的客户端地址:" + ctx.channel().remoteAddress());
       // logger.error("连接的客户端ID:" + ctx.channel().id());
        ctx.writeAndFlush("client"+ InetAddress.getLocalHost().getHostName() + "success connected！ \n");
        System.out.println("connection");
        //StaticVar.ctxList.add(ctx);
        //StaticVar.chc = ctx;
        super.channelActive(ctx);
    }
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String byteArrToHex(byte... bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
