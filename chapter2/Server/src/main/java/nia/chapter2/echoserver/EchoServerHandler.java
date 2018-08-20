package nia.chapter2.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Listing 2.1 EchoServerHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable   //标示一个 ChannelHandler 可以被多 个 Channel 安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;

        System.out.println( "Server received: " + in.toString(CharsetUtil.UTF_8));

        ctx.write(in);  //将接收到的消息写给发送者，而不冲刷出站消息
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)            //写到远程节点
                .addListener(ChannelFutureListener.CLOSE);//关闭
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
