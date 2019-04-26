package org.peek.job;

import org.peek.domain.AppInstance;
import org.peek.protocol.client.AliveClient;

import lombok.Data;

@Data
public class AliveClientWapper {

	private AliveClient client;
	private AppInstance instance;
	
}
