package com.client.netty.server.handler;

import com.client.protocol.MsgProtos;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ServerPbHandler extends ChannelInboundHandlerAdapter{
    private static final Logger log = LoggerFactory.getLogger(ServerPbHandler.class);
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
        try {
            super.channelRead(ctx, msg);
            String remoteAddress = ctx.channel().remoteAddress().toString();
            log.info("来自客户端{}的消息{}",remoteAddress, msg);
            if(msg instanceof MsgProtos.Msg){

            }else if(msg instanceof MsgProtos.TestMsg){

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MessageBean msg) throws Exception {
//        String remoteAddress = ctx.channel().remoteAddress().toString();
//
//        String content = new String(msg.getContent(), CharsetUtil.UTF_8);
//        log.info("来自客户端{}的消息{}", remoteAddress,content);
//        System.err.println("---------->"+content);
//        ctx.writeAndFlush(new MessageBean("收到了客户端"+ remoteAddress + "的消息:"+content));
//
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{}连接出异常了",ctx.channel().remoteAddress());
        ctx.close();
    }
}
