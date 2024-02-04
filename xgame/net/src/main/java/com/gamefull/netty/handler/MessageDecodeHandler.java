package com.gamefull.netty.handler;

import com.gamefull.netty.bean.MessageBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecodeHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);

        MessageBean messageBean = new MessageBean();
        messageBean.setContent(content);
        messageBean.setLen(len);
        out.add(messageBean);
    }
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        int len = in.readInt();
//        byte[] content = new byte[len];
//        in.readBytes(content);
//        MessageBean messageBean = new MessageBean();
//        messageBean.setContent(content);
//        messageBean.setLen(len);
//        out.add(messageBean);
//    }
}
