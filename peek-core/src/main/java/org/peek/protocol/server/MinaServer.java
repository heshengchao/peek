package org.peek.protocol.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.peek.protocol.ClientMinaDecoder;
import org.peek.protocol.ClientMinaEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinaServer {
	final static Charset  charset=Charset.defaultCharset();
	IoAcceptor ioAcceptor;
	private Integer port=1314;

	public void setPort(Integer port) {
		this.port = port;
	}
	
	public void start() throws IOException {
		ioAcceptor=new NioSocketAcceptor();
		log.info("begin server....");
        ioAcceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ClientMinaEncoder(charset),new ClientMinaDecoder(charset)));
        ioAcceptor.setHandler(new PeekIoHandler());
        ioAcceptor.getSessionConfig().setReadBufferSize(2048);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        ioAcceptor.bind(new InetSocketAddress(port));
	}
	
	public void stop() {
		if(ioAcceptor!=null) {
			ioAcceptor.unbind();
			ioAcceptor.dispose();
		}
	}


}
