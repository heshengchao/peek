package org.peek.protocol.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.peek.logger.LOG;
import org.peek.logger.LoggingHandler;
import org.peek.protocol.WriteBean;

import com.alibaba.fastjson.JSON;

public class PeekIoHandler extends IoHandlerAdapter{
	public WriteBean wb;
	
	public void sessionCreated(IoSession session) throws Exception {
		SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();   
        cfg.setReceiveBufferSize(2 * 1024);   
        cfg.setReadBufferSize(2 * 1024);   
        cfg.setKeepAlive(true);   
        cfg.setSoLinger(10);
    }
	
	public @Override void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOG.warn("消息发送异常："+cause.getMessage(),cause);
		session.closeOnFlush();
	}

	public @Override void messageReceived(IoSession session, Object message) throws Exception {
		wb=(WriteBean)message;
		if(LOG.isDebugEnabled())
			LOG.debug("接收到返回消息【命令："+wb.getCmd()+"】消息:"+wb.getXmlMsg());
		
		WriteBean rsp=new WriteBean();
		rsp.setXmlMsg(JSON.toJSONString(LoggingHandler.collect()));
		session.write(rsp);
	}
	public @Override void sessionIdle(IoSession session, IdleStatus status) throws Exception{
		if(LOG.isDebugEnabled())
			LOG.debug("mina状态检测消息:"+status.toString());
	}
}
