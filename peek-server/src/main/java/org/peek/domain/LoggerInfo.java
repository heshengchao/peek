package org.peek.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.peek.logger.LoggerCount;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="loggerInfo")
public class LoggerInfo extends LoggerCount{
	
    @Id
    @GeneratedValue
	private long id;
	
	private String appGroupId;
	private String appInsId;
}
