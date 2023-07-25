package com.xirui.netty.server.handler;

import com.xirui.protocol.BaseProtos;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ServerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufEncoder());
//        pipeline.addLast(new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()));
//        pipeline.addLast(new ProtobufDecoder(MsgProtos.TestMsg.getDefaultInstance()));
        pipeline.addLast(new ProtobufDecoder(BaseProtos.BaseMsg.getDefaultInstance()));
        pipeline.addLast(new PbSimpleHander1());
        pipeline.addLast(new PbSimpleHander2());
        pipeline.addLast(new PbSimpleHander3());
    }
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new MessageDecodeHandler());
//        pipeline.addLast(new MessageEncodeHandler());
//        pipeline.addLast(new ServerListenerHandler());
//    }
}
