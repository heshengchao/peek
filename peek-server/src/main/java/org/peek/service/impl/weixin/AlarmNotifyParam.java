package org.peek.service.impl.weixin;

import lombok.Data;

@Data
public class AlarmNotifyParam {
	private String value;
	private String color;
	
	public AlarmNotifyParam(String value ) {
		this.value = value;
	}
	
	public AlarmNotifyParam(String value, String color) {
		this.value = value;
		this.color = color;
	}
}
