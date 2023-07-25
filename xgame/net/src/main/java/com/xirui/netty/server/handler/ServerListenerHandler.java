package com.xirui.netty.server.handler;

import com.xirui.netty.bean.MessageBean;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ServerListenerHandler  extends SimpleChannelInboundHandler<MessageBean> {
    private static final Logger log = LoggerFactory.getLogger(ServerListenerHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("{}客户端连接进来了",ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("{}连接断开了",ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBean msg) throws Exception {
        String remoteAddress = ctx.channel().remoteAddress().toString();

        String content = new String(msg.getContent(), CharsetUtil.UTF_8);
        log.info("来自客户端{}的消息{}", remoteAddress,content);
        System.err.println("---------->"+content);
        ctx.writeAndFlush(new MessageBean("收到了客户端"+ remoteAddress + "的消息:"+content));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{}连接出异常了",ctx.channel().remoteAddress());
        ctx.close();
    }
}
