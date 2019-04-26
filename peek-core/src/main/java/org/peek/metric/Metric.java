package org.peek.metric;

import org.peek.metric.constant.MetricConstant;

import lombok.Data;

@Data
public abstract class Metric {
	String id;
	String name;
	MetricConstant metricType;
	long value;
	CountType countType;
	
	public abstract long getCount();
}
