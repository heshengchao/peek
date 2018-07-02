package org.peek.domain;

import javax.persistence.GeneratedValue;
import org.peek.logger.LoggerCount;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection="loggerInfo")
public class LoggerInfo extends LoggerCount{
	
    @Id
    @GeneratedValue
	private long id;
	
	private String appGroupId;
	private String appInsId;
}
