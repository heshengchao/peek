//package org.peek.metric;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.peek.metric.constant.MetricConstant;
//import org.peek.uitls.DateUtils;
//import org.springframework.util.StringUtils;
//
//import com.alibaba.fastjson.JSON;
//
//public class Counter {
//	private final static Map<String,Map<MetricConstant,List<Metric>>> metricList=new HashMap<>();
//	
//	private final static Map<String,List<Long>> metricCountMap=new HashMap<>();
//	private final static Map<String,String> metricIdMap=new HashMap<>();
//	
//	public static void addCount(Date time,MetricConstant metricType,long value,CountType countType) {
//		addCount(time,metricType.name(), metricType, value, countType);
//	}
//	
//	public static void addCount(Date time,String metricName,MetricConstant metricType,long value,CountType countType) {
//		String period=DateUtils.format(time,DateUtils.format_YYYYMMDDHHMM);
//	
//		String metricId= metricIdMap.get(metricName);
//		if(StringUtils.isEmpty(metricId)) {
//			metricId=metricName+"_"+period;
//			metricIdMap.put(metricName,metricId);
//			
//			List<Long> countValue=new ArrayList<>(20);
//			metricCountMap.put(metricId,countValue);
//			countValue.add(value);
//		}else if( metricId.equals(metricName+"_"+period)  ) {
//			List<Long> countValue= metricCountMap.get(metricId);
//			countValue.add(value);
//		}else {
//			List<Long> countValue= metricCountMap.get(metricId);
//			if(countValue==null) {
//				metricIdMap.remove(metricName);
//				return ;
//			}
//			String str[]=metricId.split("_");
//			
//			Map<MetricConstant,List<Metric>> metrices=metricList.get(period);
//			if(metrices==null) {
//				metrices=new HashMap<>();
//				metricList.put(period, metrices);
//			}
//			List<Metric> metricList= metrices.get(metricType);
//			if(metricList==null) {
//				metricList=new ArrayList<>();
//				metrices.put(metricType, metricList);
//			}
//			Metric	metric=new Metric();
//			
//			metricList.add(metric);
//			metric.setPeriod(str[1]);
//			metric.setMetricType(metricType);
//			metric.setId(metricName);
//			metric.setCountType(countType);
//			metric.setCount( countValue.size() );
//			long sum=0;
//			for(long v:countValue) {
//				sum+=v;
//			}
//			if(countType==CountType.AVG) {
//				metric.setValue(sum/countValue.size());
//			}else {
//				metric.setValue(sum);
//			}
//		}
//		
//	}
//
//	public static String popByTime(String time) {
//		Map<MetricConstant,List<Metric>> metrices=metricList.get(time);
//		if(metrices==null) {
//			return null;
//		}else {
//			return JSON.toJSONString(metrices);
//		}
//	};
//	
//}
