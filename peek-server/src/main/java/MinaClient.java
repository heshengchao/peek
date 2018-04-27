
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.peek.collect.protocol.ClientMinaDecoder;
import org.peek.collect.protocol.ClientMinaEncoder;
import org.peek.collect.protocol.WriteBean;
import org.peek.exception.ConnectAccessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinaClient {
	private final static Map<String,InetSocketAddress> netMap=new HashMap<String,InetSocketAddress>();
	final static Charset  charset=Charset.defaultCharset();
	
	final static SequenceGenerator instance = new SequenceGenerator();
	
	final static ThreadPoolExecutor excutor=new ThreadPoolExecutor(1, 10,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());
	
	/**发送消息 */
	public static WriteBean sendMsg(String host,Integer port,String xmlMsg ){
		
		final IoConnector connector = new NioSocketConnector(); // 创建一个非阻塞的客户端程序
		connector.setConnectTimeoutMillis(1000);  // 设置链接超时时间
		
		CustomerHandler handler=new CustomerHandler();
		// 添加过滤器
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ClientMinaEncoder(charset),new ClientMinaDecoder(charset)));
		connector.getFilterChain().addLast("log", new LoggingFilter());  
		
		// 添加业务逻辑处理器类
		connector.setHandler(handler);// 添加业务处理

		InetSocketAddress adds=netMap.get(host+port);
		if(adds==null){
			//添加Try-catch。解决异常：java.io.IOException: Too many open files
			try{
				adds=new InetSocketAddress(host, port);
			}catch(Exception e){
				connector.dispose();
				throw new ConnectAccessException("连接服务器："+host+":"+port+"失败："+e.getMessage(),e);
			}
			netMap.put(host+port, adds);
			log.debug("添加新服务器："+host+":"+port);
		}
		
		ConnectFuture future = connector.connect(adds);// 创建连接
		future.awaitUninterruptibly();// 等待连接创建完成
		IoSession session=null;
		try{
			session = future.getSession();// 获得session
		}catch(Exception e){
			connector.dispose();
			log.error("打开连接["+host+":"+port+"]异常，请注意检查！详细："+e.getMessage(),e);
			return null;
		}
		
		WriteBean bean =new WriteBean();
		bean.setXmlMsg(xmlMsg);
		bean.setCmd((short)1);
		bean.setSeq(instance.getNextMessageSeq());
		session.write(bean);
		
		IoSession closeSession= session;
		excutor.execute(new Runnable() {
			@Override public void run() {
				try {
					Thread.sleep(30000);
					if(closeSession.isActive()) {
						if(closeSession.getCloseFuture().awaitUninterruptibly(3000)){
							log.debug("消息正常接收，关闭连接！");
						}else{
							log.warn("接收消息超时，关闭连接！");
						}
						connector.dispose();
					}
				} catch (InterruptedException e) {
					log.error(e.getMessage(),e);
				}
			}
		});
		
		
		return handler.wb;
	}
	
	static class CustomerHandler extends IoHandlerAdapter{
		public WriteBean wb;
		
		public void sessionCreated(IoSession session) throws Exception {
			SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();   
	        cfg.setReceiveBufferSize(2 * 1024);   
	        cfg.setReadBufferSize(2 * 1024);   
	        cfg.setKeepAlive(true);   
	        cfg.setSoLinger(10);
	    }
		
		public @Override void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			log.error("消息发送异常："+cause.getMessage(),cause);
			session.closeOnFlush();
		}

		public @Override void messageReceived(IoSession session, Object message) throws Exception {
			wb=(WriteBean)message;
			log.debug("接收到返回消息【命令："+wb.getCmd()+"】消息:"+wb.getXmlMsg());
			session.closeOnFlush();
		}
		public @Override void sessionIdle(IoSession session, IdleStatus status) throws Exception{
			log.debug("mina状态检测消息:"+status.toString());
		}
	};
	
	static class SequenceGenerator {
		
		private short nextMessageSeq = 0;
		
		public short getNextMessageSeq() {
			return ++nextMessageSeq<0?0:nextMessageSeq;
		}

	}
}
