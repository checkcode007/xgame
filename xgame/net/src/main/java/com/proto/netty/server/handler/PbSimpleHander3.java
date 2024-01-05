package com.proto.netty.server.handler;

import com.proto.BaseProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PbSimpleHander3 extends SimpleChannelInboundHandler< BaseProtos.BaseMsg> {
    Logger log = LoggerFactory.getLogger(PbSimpleHander3.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  BaseProtos.BaseMsg msg) throws Exception {
        log.info("PbSimpleHander3========>"+msg);
//        ctx.fireChannelRead(msg);
    }
}
