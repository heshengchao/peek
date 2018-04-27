package org.peek.protocol;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgHanler extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        // 出现异常
        log.error("--------exception--------");
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        // 从服务器中接收到消息后的处理
        log.info("--------msg receive--------");
        log.info("Message:{}" + message.toString());
        super.messageReceived(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // 往服务器中发送消息
        log.info("--------msg sent--------");
        super.messageSent(session, message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // 当session被创建的时候调用
        log.info("--------session create--------");
        super.sessionCreated(session);
    }
}
