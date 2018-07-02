package org.peek.service.impl;

import org.peek.uitls.DateUtils;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IdGenerate {
	private static final Object syncTag=new Object();
	private int index=0;
	
	/**得到正向单号
	 * @return
	 */
	public  long getId() {
		String pre=DateUtils.formatNow(DateUtils.format_YYYYMMDDHHMMSSSSS);
		synchronized (syncTag) {
			return Long.parseLong(getId(String.valueOf(System.currentTimeMillis())));
		}
	}

	
	private String  getId(String prefix) {
		synchronized (syncTag) {
			int value=++index;
			if (value>998) {
				index=0;
			}
			return prefix+String.format("%0" + 3 + "d", value);
		}
	}
}
