package com.proto.netty.server.handler;

import com.proto.MsgProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PbSimpleHander2 extends SimpleChannelInboundHandler<MsgProtos.TestMsg> {

    Logger log = LoggerFactory.getLogger(PbSimpleHander2.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgProtos.TestMsg msg) throws Exception {
        log.info("PbSimpleHander2-------->"+msg);
        ctx.fireChannelRead(msg);

    }
}
