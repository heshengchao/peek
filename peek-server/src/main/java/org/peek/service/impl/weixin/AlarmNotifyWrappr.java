package org.peek.service.impl.weixin;

import java.util.Map;

import lombok.Data;

@Data
public class AlarmNotifyWrappr {

	private String touser;
	private String template_id;
	private String topcolor;
	private Map<String,AlarmNotifyParam> data;
	
}
