package org.peek.protocol;

import lombok.Data;

@Data
public class WriteBean {
	private short seq;
	/**命令编码*/
	private short cmd;
	/**系统版本*/
	private short version;
	/**传输的XML数据 */
	private String xmlMsg;
}
