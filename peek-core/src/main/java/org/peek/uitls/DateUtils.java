package org.peek.uitls;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger log=LoggerFactory.getLogger(DateUtils.class);
	  /**
     * 默认日期格式
     */
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    
    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd_HH-mm-ss";
    
    public static final String YYYYMMDDHHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String YYYYMMddHHmmss = "yyyyMMddHHmmss";
    
    /**默认时间格式：yyyy-MM-dd HH:mm:ss */
    public static final SimpleDateFormat default_format = new SimpleDateFormat(YYYYMMDDHHMMSS);
    /**默认时间格式：yyyy-MM-dd HH:mm:ss */
    public static final SimpleDateFormat format_MM_DD = new SimpleDateFormat("MM-dd");
    /**日期时间格式：yyyy-MM-dd*/
    public static final SimpleDateFormat format_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    /**yyyyMMdd*/
    public static final SimpleDateFormat format_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    /**yyyyMM*/
    public static final SimpleDateFormat format_YYYYMM = new SimpleDateFormat("yyyyMM");
    /**yyMMddHHmmss*/
    public static final SimpleDateFormat format_YYMMDDHHMMSS = new SimpleDateFormat("yyMMddHHmmss");
    /**yyyyMMddHHmmss*/
    public static final SimpleDateFormat format_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    /**yyyyMMddHHmmssSSS*/
    public static final SimpleDateFormat format_YYYYMMDDHHMMSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat format_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat format_YYYY_MM_DD_HH_MM_SS_SSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat format_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
    /** <默认构造函数>
     */
    private DateUtils()
    { }
    
    /** <字符串转换成日期>
     * <如果转换格式为空，则利用默认格式进行转换操作>
     * @param str 字符串
     * @param format 日期格式
     * @return 日期
     * @see [类、类#方法、类#成员]
     */
    public static Date str2Date(String str, String format){
        try {
			return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		return null;
    }
    
    public static Date parseDate(String dateStr) {
		return parseDate(dateStr, default_format);
	}
    
    public static Date parseDate(String dateStr,SimpleDateFormat format)  {
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e.getMessage());
		}
	}

    public static String format(Date date) {
		return format(date, format_YYYY_MM_DD_HH_MM_SS);
	}
    
    /** 
     * @Description:格式化并返回当前时间
     * @author heshengchao 
     * @Date:2017年8月6日上午11:53:57
     */ 
    public static String formatNow() {
		return format(new Date(), format_YYYY_MM_DD_HH_MM_SS);
	}
    
    /** 
     * @Description:格式化并返回当前时间
     * @author heshengchao 
     * @Date:2017年8月6日上午11:53:57
     */ 
    public static String formatNow(SimpleDateFormat format) {
		return format(new Date(), format);
	}
    
    public static String format(Date date, SimpleDateFormat format) {
    	return date==null?null:format.format(date);
	}
    public static String formatDateTime(Date date, String format) {
    	return format(date, new SimpleDateFormat(format));
	}
    
    
    public static String formatDateTime(Date date, Date defaultDate, String format) {
    	return date!=null?formatDateTime(date, format):formatDateTime(defaultDate, format);
	}
    
    /** <时间戳转换为字符串>
     * <功能详细描述>
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String timestamp2Str(Timestamp time)
    {
        Date date = new Date(time.getTime());
        return format(date, format_YYYY_MM_DD_HH_MM_SS);
    }
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Timestamp str2Timestamp(String str)
    {
        Date date = str2Date(str, YYYYMMDDHHMMSS);
        return new Timestamp(date.getTime());
    }
    
    /**
     * string装int的时间
     *
     * @param timeStr
     * @return
     */
    public static long getIntTimeByString(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(timeStr);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
            return 0;
        }
    }

	/**
	 * 获取2个时间相隔几分钟
	 */
	public static int getBetweenMinuteNumber(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}
		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}
		long timeNumber = -1l;
		long TIME = 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return (int) timeNumber;
	}

   /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    public static int differentHoursByMillisecond(Date date1,Date date2){
        int hours = (int) ((date2.getTime() - date1.getTime()) / (1000*3600));
        return hours;
    }

   
}