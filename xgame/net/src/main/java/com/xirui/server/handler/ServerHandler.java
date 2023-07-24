package com.xirui.server.handler;

import com.xirui.common.handler.MessageDecodeHandler;
import com.xirui.common.handler.MessageEncodeHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MessageDecodeHandler());
        pipeline.addLast(new MessageEncodeHandler());
        pipeline.addLast(new ServerListenerHandler());
    }
}
