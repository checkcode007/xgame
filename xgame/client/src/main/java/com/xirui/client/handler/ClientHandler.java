package com.xirui.client.handler;

import com.xirui.protocol.BaseProtos;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;


public class ClientHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ProtobufEncoder());
//        pipeline.addLast(new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()));
//        pipeline.addLast(new ProtobufDecoder(MsgProtos.TestMsg.getDefaultInstance()));
        pipeline.addLast(new ProtobufDecoder(BaseProtos.BaseMsg.getDefaultInstance()));
        pipeline.addLast(new ClientPbHandler());
    }
}
