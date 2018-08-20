package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.2 EchoServer class
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
        throws Exception {
        if (args.length != 1) {
            System.err.println("缺少参数: " + EchoServer.class.getSimpleName() +
                " <port>"
            );
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();//服务处理

        EventLoopGroup group = new NioEventLoopGroup();//事件管理组
        try {
            ServerBootstrap b = new ServerBootstrap();//服务器引导器
            b.group(group);  //引导器绑定事件管理组
            b.channel(NioServerSocketChannel.class);     //引导指定渠道
            b.localAddress(new InetSocketAddress(port));  //引导指定地址
            b.childHandler(new ChannelInitializer<SocketChannel>() {//引导 业务处理
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);//@Shareable  总是使用同一个实例（单例？）
                    }
                });

            ChannelFuture f = b.bind().sync(); //bind绑定  sync当前Thread阻塞直到完成
            System.out.println(EchoServer.class.getName() + " 启动并监听如下链接 " + f.channel().localAddress());
            f.channel().closeFuture().sync();  //获取closeFuture  sync阻塞直到完成
            System.out.println( " f.channel().closeFuture().sync() 获取closeFuture  sync阻塞直到完成");
        } finally {
            group.shutdownGracefully().sync();
            System.out.println("关闭了！！");
        }
    }
}
