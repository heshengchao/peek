package org.peek.domain;

import org.peek.logger.LoggerCount;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection="loggerInfo")
public class LoggerInfo extends LoggerCount{
	private String appGroupId;
	private String appInsId;
}
