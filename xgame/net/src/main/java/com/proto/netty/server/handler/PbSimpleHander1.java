package com.proto.netty.server.handler;

import com.proto.BaseProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PbSimpleHander1 extends SimpleChannelInboundHandler<BaseProtos.BaseMsg> {
    Logger log = LoggerFactory.getLogger(PbSimpleHander1.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseProtos.BaseMsg msg) throws Exception {
        log.info("PbSimpleHander1========>"+msg);
        ctx.fireChannelRead(msg);
    }
}
