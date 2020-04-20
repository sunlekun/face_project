package com.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * 日期格式化工具类
 *
 * @author lekun.sun
 */
public class DateFormatUtil {

    private DateFormatUtil() {
    }

    private static final Map<String, DateFormat> formatMap = new HashMap<String, DateFormat>();

    /**
     * @param pattern pattern
     * @return DateFormat
     */
    static public DateFormat getDateFormat(String pattern) {
//        DateFormat format = formatMap.get(pattern);
//        if (format == null) {
//            synchronized (formatMap) {
//                format = formatMap.get(pattern);
//                if (format == null) {
//                    format = new SimpleDateFormat(pattern);
//                    formatMap.put(pattern, format);
//                }
//            }
//        }
//        return format;
    	// 线程安全问题，每次创建新对象
        return new SimpleDateFormat(pattern);
    }

    /**
     * @return date format
     */
    static public String getCurrentDT() {
        return getCurrentDT("yyyyMMdd");
    }
    
    /**
     * @param pattern pattern
     * @return date format
     */
    static public String getCurrentDT(String pattern) {
        return getDateFormat(pattern).format(Calendar.getInstance().getTime());
    }

    /**
     * 返回当前时间yyyyMMddHHmmss
     * 
     * @return date format
     */
    static public String getCurrentTime() {
        return getDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    /**
     * @param nextDays next days
     * @param pattern  format pattern
     * @return date format
     */
    static public String getNextDT(int nextDays, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, nextDays);
        return getDateFormat(pattern).format(cal.getTime());
    }

    /**
     * @param date    java.util.Date
     * @param pattern pattern
     * @return date format
     */
    static public String getDTFormat(java.util.Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

	/**
	 * 格式化日期
	 * 
	 * @param myDateTime
	 * @return ex. 20011217 ==> 2001-12-17 ex. 151617 ==> 15:16:17 ex.
	 *         20011217151617 ==> 2001-12-17 15:16:17
	 */
	public static String formateDateTime(String myDateTime) throws Exception {
		if (myDateTime == null)
			return "";

		String rtnDateTime = "";
		if (myDateTime.length() == 8 || myDateTime.length() == 14) {
			rtnDateTime = myDateTime.substring(0, 4) + "-" + myDateTime.substring(4, 6) + "-"
					+ myDateTime.substring(6, 8);
			if (myDateTime.length() == 14) {
				rtnDateTime = rtnDateTime + " ";
				myDateTime = myDateTime.substring(8);
			}
		}
		if (myDateTime.length() == 6) {
			rtnDateTime = rtnDateTime + myDateTime.substring(0, 2) + ":" + myDateTime.substring(2, 4) + ":"
					+ myDateTime.substring(4, 6);
		}
		return rtnDateTime;
	}
	
	
	/**
	 *取当月第一天
	 */
	public static String dayOfMonth() throws Exception {
		LocalDate today = LocalDate.now();//取当前时间
		LocalDate firstDate = today.withDayOfMonth(1);//当月第一天
		return firstDate.toString().replace("-", "");
	}
	
	/**获取上n个小时整点小时时间
     * @param date
     * @return
     */
    public static String getLastHourTime(Date date,int n){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)-n);
        date = ca.getTime();
        return sdf.format(date);
    }
	
    /**获取上n个小时整点小时时间
     * @param date
     * @return
     */
    public static Date parse(Date date,int n) throws ParseException {
    	 Calendar ca = Calendar.getInstance();
         ca.set(Calendar.MINUTE, 0);
         ca.set(Calendar.SECOND, 0);
         ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)-n);
         date = ca.getTime();
        return date;
       // StringUtils.isBlank(strDate) ? null :  new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate)
    }
    
    public static void main(String[] args) throws ParseException {
		System.out.println(parse(new Date(),0));
	}
	}

