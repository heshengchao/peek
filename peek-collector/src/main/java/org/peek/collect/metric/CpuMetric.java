package org.peek.collect.metric;

import org.peek.metric.CountType;
import org.peek.metric.Metric;
import org.peek.metric.constant.MetricConstant;

/**
 * @author heshengchao
 *
 */
public class CpuMetric extends Metric{

	public CpuMetric() {
		super.setCountType(CountType.AVG);
		super.setName("cpu");
		super.setMetricType(MetricConstant.CpuRate);
	}
	
	public long getCount() {
		return 0;
	}
}
