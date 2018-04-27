package org.peek.collect.protocol;

import lombok.Data;

@Data
public class WriteBean {
	private short seq;
	/**命令编码*/
	private short cmd;
	/**传输的XML数据 */
	private String xmlMsg;
}
