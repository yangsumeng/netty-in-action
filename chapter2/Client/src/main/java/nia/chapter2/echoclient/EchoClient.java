package nia.chapter2.echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.4 Main class for the client
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start()
        throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//创建连接以及 处理入站和出站数据
        try {
            Bootstrap b = new Bootstrap(); //创建引导器
            b.group(group)                 //引导器设置EventLoopGroup业务逻辑
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                        throws Exception {
                        ch.pipeline().addLast(          //安装
                             new EchoClientHandler());  //业务
                    }
                });
            ChannelFuture f = b.connect().sync();       //阻塞直到连接
            System.out.println("ChannelFuture:阻塞直到连接");
            f.channel().closeFuture().sync();           //阻塞直到关闭
            System.out.println("closeFuture:阻塞直到关闭");
        } finally {
            group.shutdownGracefully().sync();          //阻塞直到释放资源
            System.out.println("finally:阻塞直到释放资源");
        }
    }

    public static void main(String[] args)
            throws Exception {
        if (args.length != 2) {
            System.err.println("缺少参数: " + EchoClient.class.getSimpleName() + " <host> <port>");
            return;
        }

        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        System.out.println(host+":"+port);
        new EchoClient(host, port).start();
    }
}

