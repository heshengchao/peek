package org.peek.metric.constant;

public enum MetricConstant implements EnumAware {

	CpuRate("CPU利用率"),
	MemRate("内存利用率"),
	ThreadCount("线程数"),
	HttpCount("HttpCount统计"),
	SpringServiceCount("Service统计"),
	jdbcCount("jdbc统计");
	
	private String desc;

	MetricConstant(String desc) {
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
