package com.proto.client.handler;

import com.proto.bean.MessageBean;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ClientListenerHandler extends SimpleChannelInboundHandler<MessageBean> {

    private static final Logger log = LoggerFactory.getLogger(ClientListenerHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        log.info("{}连上了服务器",ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        log.info("{}断开了服务器",ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBean msg) throws Exception {
        log.info("来自服务端的消息:{}",new String(msg.getContent(), CharsetUtil.UTF_8));
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("{}连接出异常了",ctx.channel().remoteAddress());
        ctx.close();
    }


}
