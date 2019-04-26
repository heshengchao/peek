package org.peek.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.peek.uitls.DateUtils;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IdGenerate {
	private static final Object syncTag=new Object();
	private int index=0;
	
	static final String[] str=new String[] {"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
	static final String[] strMonth=new String[] {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    
	/**得到正向单号
	 * @return
	 */
	public  long getId() {
		String pre=DateUtils.formatNow(DateUtils.format_YYYYMMDDHHMMSSSSS);
		synchronized (syncTag) {
			return Long.parseLong(getId(String.valueOf(System.currentTimeMillis())));
		}
	}
	
	/**得到正向单号
	 * @return
	 */
	public  String getStrsId() {
		synchronized (syncTag) {
			Date now=new Date();
			Calendar cal=Calendar.getInstance();
			cal.setTime(now);
			String midStr=DateUtils.formatDateTime(now,"mmssSSS").substring(0,6);
			String sy=str[(cal.get(Calendar.YEAR)-2018)%str.length];
			String sm=strMonth[cal.get(Calendar.MONTH)%strMonth.length];
			String sd=strMonth[cal.get(Calendar.DAY_OF_MONTH)%strMonth.length];
			String sh=strMonth[cal.get(Calendar.HOUR_OF_DAY)%strMonth.length];
			return getId("R"+sy+sm+sd+sh+midStr);
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
