package org.peek.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.peek.logger.LoggerCount;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="app_log_info")
public class LoggerInfo extends LoggerCount{
	
    @Id
    @GeneratedValue
	private long id;
	
	private String insGroupId;
	private String insId;
}
