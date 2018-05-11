package org.peek.domain;

import org.peek.logger.LoggerCount;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoggerInfo extends LoggerCount{
	private String appGroupId;
	private String appInsId;
}
