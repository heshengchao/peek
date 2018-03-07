package org.peek.metric;

import org.peek.metric.constant.MetricConstant;

import lombok.Data;

@Data
public class Metric {
	String id;
	MetricConstant metricType;
	int count;
	long value;
	String period;
	CountType countType;
	
}
