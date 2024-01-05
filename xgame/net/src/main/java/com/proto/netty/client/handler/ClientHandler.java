package com.proto.netty.client.handler;

import com.proto.netty.handler.MessageDecodeHandler;
import com.proto.netty.handler.MessageEncodeHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class ClientHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MessageEncodeHandler());
        pipeline.addLast(new MessageDecodeHandler());
        pipeline.addLast(new ClientListenerHandler());
    }
}
