package org.peek.protocol.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.peek.logger.LOG;
import org.peek.protocol.ClientMinaDecoder;
import org.peek.protocol.ClientMinaEncoder;
import org.springframework.util.StringUtils;


public class MinaServer {
	final static Charset  charset=Charset.defaultCharset();
	IoAcceptor ioAcceptor;
	private int port=1314;
	private String ip=null;

	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public void start() throws IOException {
		ioAcceptor=new NioSocketAcceptor();
		LOG.info("start peek server....");
        ioAcceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ClientMinaEncoder(charset),new ClientMinaDecoder(charset)));
        ioAcceptor.setHandler(new PeekIoHandler());
        ioAcceptor.getSessionConfig().setReadBufferSize(2048);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        InetSocketAddress is;
        if(!StringUtils.isEmpty(ip)) {
        	is= new InetSocketAddress(ip,port);
        }else {
        	is= new InetSocketAddress(port);
        }
        ioAcceptor.bind(is);
	}
	
	public void stop() {
		if(ioAcceptor!=null && ioAcceptor.isActive()) {
			try {
				LOG.info(" peek server stoping....");
				ioAcceptor.unbind();
				ioAcceptor.dispose(true);
				LOG.info(" peek server stoped....");
			}catch (Throwable e) {
				LOG.warn(e.getMessage(), e);
			}
		}
	}


}
