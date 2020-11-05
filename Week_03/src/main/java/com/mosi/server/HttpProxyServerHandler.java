package com.mosi.server;


import com.mosi.filter.HttpHeaderFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpProxyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String host = "127.0.0.1";
        int port = 8809;


        FullHttpRequest request = (FullHttpRequest) msg;

        HttpHeaderFilter httpHeaderFilter = new HttpHeaderFilter();
        httpHeaderFilter.doFilter(request);

        request.setUri("http://127.0.0.1:8809/test");


        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new HttpProxyInitializer(ctx.channel()));

        ChannelFuture cf = bootstrap.connect(host, port);
        cf.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    future.channel().writeAndFlush(msg);
                } else {
                    ctx.channel().close();
                }
            }
        });

    }
}
