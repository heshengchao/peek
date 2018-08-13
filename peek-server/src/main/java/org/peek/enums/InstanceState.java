package org.peek.enums;

public enum InstanceState implements EnumAware {

	online("在线"),offline("离线"),connectFail("链接失败");

	
	private String desc;
	InstanceState(String description){
		this.desc = description;
	}
	
	public String getDesc() {
		return this.desc;
	}

}
