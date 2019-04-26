package org.peek.protocol;

import java.io.IOException;

import org.peek.protocol.server.MinaServer;


public class MinaServerTest {
	
	
	public static void main(String[] args) throws IOException {
		MinaServer server=new MinaServer(MinaServer.defaltPort);
		server.start();
	}
}
