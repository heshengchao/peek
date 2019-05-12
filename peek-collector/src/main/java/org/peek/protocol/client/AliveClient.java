package org.peek.protocol.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.peek.exception.ConnectAccessException;
import org.peek.protocol.ClientMinaDecoder;
import org.peek.protocol.ClientMinaEncoder;
import org.peek.protocol.WriteBean;

import lombok.extern.slf4j.Slf4j;

/** 长链接会话
 * @author heshengchao
 */
@Slf4j
public class AliveClient {
	final static Charset  charset=Charset.defaultCharset();
	
	final static SequenceGenerator instance = new SequenceGenerator();
	
//	static final Map<String,IoSession> sessionMap=new HashMap<>();
	private Callback callback;
	private String host;
	private int port;
	//TCP会话
	private IoSession session;
	private IoConnector connector;
	
	public AliveClient(String host,int port,Callback callback) {
		this.callback=callback;
		this.host=host;
		this.port=port;
		
//		session=sessionMap.get(host+port);
//		if(session==null) {
//			session=getSession(host,port);
//		}
	}
	
	/**发送消息 */
	public boolean sendMsg(String xmlMsg ){
		
		if(session==null || !session.isActive()) {
			log.warn("未建立与服务器({})的链接",host+":"+port);
//			sessionMap.remove(host+port);
			throw new ConnectAccessException("连接服务器："+host+":"+port+"链接异常");
		}
		
		WriteBean bean =new WriteBean();
		bean.setXmlMsg(xmlMsg);
		bean.setCmd((short)1);
		bean.setSeq(instance.getNextMessageSeq());
		session.write(bean);
		
		return true;
	}
	
	public void close() {
		if(session!=null) {
			session.closeOnFlush();
		}
		if(connector!=null) {
			connector.dispose();
		}
	}
	
	public boolean connection() {
		if(connector!=null) {
			log.warn("network has connected!");
			return false;
		}
		connector = new NioSocketConnector(); // 创建一个非阻塞的客户端程序
		connector.setConnectTimeoutMillis(1000);  // 设置链接超时时间
		
		CustomerHandler handler=new CustomerHandler(host,port);
		// 添加过滤器
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ClientMinaEncoder(charset),new ClientMinaDecoder(charset)));
		connector.getFilterChain().addLast("log", new LoggingFilter());  
		
		// 添加业务逻辑处理器类
		connector.setHandler(handler);// 添加业务处理

		InetSocketAddress adds;
		//添加Try-catch。解决异常：java.io.IOException: Too many open files
		try{
			adds=new InetSocketAddress(host, port);
		}catch(Exception e){
			connector.dispose();
			throw new ConnectAccessException("连接服务器："+host+":"+port+"失败："+e.getMessage(),e);
		}
		
		ConnectFuture future = connector.connect(adds);// 创建连接
		future.awaitUninterruptibly();// 等待连接创建完成
		try{
			this.session = future.getSession();// 获得session
			
		}catch(Throwable e){
			callback.connectFail();
			connector.dispose();
			log.error("打开连接["+host+":"+port+"]异常，请注意检查！详细："+e.getMessage(),e);
			connector=null;
			return false;
		}
		return true;
	}
	
	class CustomerHandler extends IoHandlerAdapter{
		public WriteBean wb;
		String host;
		int port;
		CustomerHandler(String host,int port){
			this.host=host;
			this.port=port;
		}
		
		public void sessionCreated(IoSession session) throws Exception {
			SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();   
	        cfg.setReceiveBufferSize(2 * 1024);   
	        cfg.setReadBufferSize(2 * 1024);   
	        cfg.setKeepAlive(true);   
	        cfg.setSoLinger(0);
	    }
	    @Override
	    public void sessionClosed(IoSession session) throws Exception {
	    	log.info("连接["+host+":"+port+"]断开");
//			sessionMap.remove(host+port);
			callback.close();
			connector.dispose();
	    }
		public @Override void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			log.warn("连接["+host+":"+port+"]异常："+cause.getMessage(),cause);
//			sessionMap.remove(host+port);
			session.closeNow();
			callback.close();
		}

		public @Override void messageReceived(IoSession session, Object message) throws Exception {
			wb=(WriteBean)message;
			if(log.isDebugEnabled())
				log.debug("接收到消息【命令："+wb.getCmd()+"】消息:"+wb.getXmlMsg());
			callback.action(wb);
		}
		public @Override void sessionIdle(IoSession session, IdleStatus status) throws Exception{
			if(log.isDebugEnabled())
				log.debug("mina状态检测消息:"+status.toString());
		}
	};
	
	static class SequenceGenerator {
		
		private short nextMessageSeq = 0;
		
		public short getNextMessageSeq() {
			return ++nextMessageSeq<0?0:nextMessageSeq;
		}

	}
	public static interface Callback {
		
		public void action(WriteBean msg) ;

		public void close() ;
		
		public void connectFail() ;
		
	}
}
