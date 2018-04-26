package org.peek.collect.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	final static Charset  charset=Charset.defaultCharset();
	
	public void start() {
		IoAcceptor ioAcceptor=new NioSocketAcceptor();
        System.out.println("begin server....");
        ioAcceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ClientMinaEncoder(charset),new ClientMinaDecoder(charset)));
        ioAcceptor.setHandler(new HelloWorldHandler());
        ioAcceptor.getSessionConfig().setReadBufferSize(2048);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        try {
            ioAcceptor.bind(new InetSocketAddress(14444));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
