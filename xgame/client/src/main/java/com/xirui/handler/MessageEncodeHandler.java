package com.xirui.handler;

import com.xirui.bean.MessageBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncodeHandler extends MessageToByteEncoder<MessageBean> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageBean msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
