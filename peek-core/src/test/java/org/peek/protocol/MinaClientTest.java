package org.peek.protocol;
import org.junit.Test;
import org.peek.protocol.client.MinaClient;

public class MinaClientTest {

	@Test
	public void sendMsg() {
		MinaClient.sendMsg("10.115.88.20", 1314, "");
	}
}
