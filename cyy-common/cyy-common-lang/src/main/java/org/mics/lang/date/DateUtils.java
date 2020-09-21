package org.mics.lang.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具
 * 各种时间转化
 * @date 2015-10-1
 * @version 1.0
 * @author mics
 */
public  class DateUtils {
	/**
	 * 日历
	 */
	private static final Calendar calendar = Calendar.getInstance(Locale.CHINA);
	
	/**
	 * 按指定的表达式把当前日期转化日期类型
	 * @param pdate 需要转化的时间
	 * @param pattern 表达式
	 * @return 格式化之后的字符串
	 * @throws ParseException
	 */
	public static  Date getDateYMD(final Date pdate, final String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String txt = simpleDateFormat.format(pdate);
		Date date;
		try {
			date = simpleDateFormat.parse(txt);
		} catch (ParseException e) {
			throw new DateParseException("时间转化错误！");
		}
		return date;
	}
	
	/**
	 * 按照指定的表达式把当前日期转化字符串类型
	 * @param pdate 需要转化的时间
	 * @param pattern 表达式
	 * @return 格式化之后的字符串
	 * @throws ParseException
	 */
	public static String getDateYMDS(final Date pdate, final String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String txt = simpleDateFormat.format(pdate);
		return txt;
	}
	

	
	
	/**
	 * 根据参数date取得月份
	 * @param date 需要获取月份的时间
	 * @return 月份
	 */
	public static int getMonth(final Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 根据参数date取得年份
	 * @param date 需要获取年份的时间
	 * @return 年份
	 */
	public static int getYear(final Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 根据参数date取得属于该月份的天数
	 * @param date 时间
	 * @return 属于该时间中月份的第几天
	 */
	public static int getDay(final Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 根据参数date取得属于该天的小时数
	 * @param date 时间
	 * @return 属于该时间中的天数的第几个小时
	 */
	public static int getHour(final Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR);
	}
	
	/**
	 * 根据参数date取得属于该小时的分钟数
	 * @param date 时间
	 * @return 属于该时间中的小时中的第几份
	 */
	public  static int getMinute(final Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 根据参数date取得属于该分的秒数
	 * @param date 时间
	 * @return 属于该时间中的分中的第几秒
	 */
	public  static int getSecond(final Date date){
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 判断两个时间是否是相等的一天
	 * @param date1 时间1
	 * @param date2 时间2
	 * @return 返回两个时间是否相当
	 */
	public static boolean equalDay(final Date date1,final Date date2){
		return getYear(date1) == getYear(date2)
		 			&& getMonth(date1) == getMonth(date2) 
		 			&& getDay(date1) == getDay(date2);
	}
	
	/**
	 * 给date时间增加月
	 * @param date  时间
	 * @param month 增加月数
	 * @return  增加月数之后的时间
	 */
	public static Date addMonth(final Date date,final int month) {
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}
	
	/**
	 * 给指定时间增加天数
	 * @param date  时间
	 * @param day 需要增加的天数
	 * @return 增加天数之后的时间
	 */
	public static Date addDay(final Date date,final int day){
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	/**
	 * 判断date1比date2大几个月
	 * 传入参数的时候注意date1应该date2大
	 * @param date1 时间1 
	 * @param date2 时间2
	 * @return 相差的月份
	 */
	public static int compareMonth(final Date date1,final Date date2){
		int differYear = getYear(date1) - getYear(date2);
		int differMonth = getMonth(date1) - getMonth(date2);
		return differYear* 12 + differMonth;
	}
	
	/**
	 * 在同一个月中判断date1比date2大多少天
	 * 传入参数的时候注意date1应该date2大
	 * @param date1 时间1 
	 * @param date2 时间2
	 * @return  相差天数
	 */
	public static int compareDayInMonth(final Date date1,final Date date2){
		int differDay = getDay(date1) - getDay(date2);
		return differDay;
	}

	/**
	 * 比较两个时间相差自然天数(非精确到小时)
	 * @author mics
	 * @date 2018年8月23日
	 * @version 1.0
	 * @param sourceDate yyyy-MM-dd
	 * @param targetDate yyyy-MM-dd
	 */
	public static int compareDay(final Date sourceDate,final Date targetDate){
		if(sourceDate == null ||  targetDate == null){
			throw new IllegalArgumentException("时间比较，源时间和目标时间不能为空");
		}
		try {
			long time1 = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(sourceDate)).getTime();
			long time2 = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(targetDate)).getTime();
			int day = (int) ((time1-time2)/24/60/60/1000);
			return day;
		} catch (ParseException e) {
			throw new DateParseException("时间转化异常！");
		}
	}
	
	/**
	 * 根据时间查询当前月第一天
	 * @param date 时间
	 * @return 第一天
	 */
	public static Date getFirstDate(final Date date) {
		calendar.setTime(date);
		int day = calendar.getActualMinimum(Calendar.DATE);
		calendar.set(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 根据时间查询当前月最后一天
	 * @param date 时间
	 * @return 最后一天
	 */
	public static Date getEndDate(final Date date) {
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	/**
	 * 根据该月份有多少天
	 * @param month 月份
	 * @return 天数
	 */
	public static int getCountDayInMonth(int month) {
		calendar.set(Calendar.MONTH, month);
		return  calendar.getActualMaximum(Calendar.DATE);
	}
	
	/**
	 * 根据时间查询当月所有字符串化的日期
	 * @param date 时间
	 * @return 字符串化之后的日期
	 * @throws ParseException 
	 */
	public static List<String> getAllDateInMonth(final Date date) throws ParseException {
		List<String> dates = new ArrayList<String>();
		Date firstDate = DateUtils.getFirstDate(date);
		Date lastDate = DateUtils.getEndDate(date);
		dates.add("'"+DateUtils.getDateYMDS(firstDate,"yyyy-MM-dd")+"'");
		Date temp = firstDate;
		while(temp.before(lastDate)){
			temp = DateUtils.addDay(temp, 1);
			dates.add("'"+DateUtils.getDateYMDS(temp,"yyyy-MM-dd")+"'");
		}
		dates.add("'"+DateUtils.getDateYMDS(lastDate,"yyyy-MM-dd")+"'");
		return dates;
	}

	/**
	 * 减去天数
	 * @author mics
	 * @date 2018年8月14日
	 * @version 1.0
	 */
	public static Date subDay(final Date date, final int deadline) {
		calendar.setTime(date);
		String deadlines = "-"+deadline;
		int a = Integer.valueOf(deadlines);
		calendar.add(Calendar.DATE, a);
		return calendar.getTime();
	}
	
}
