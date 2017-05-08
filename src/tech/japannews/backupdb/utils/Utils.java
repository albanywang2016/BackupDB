package tech.japannews.backupdb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class Utils {
	public static String formatTime(LocalDateTime now, String pattern) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
		return now.format(format);
	}

	public static String GetOneMonthBefore(String now, String pattern) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(now));
		c.add(Calendar.MONTH, -1);  
		return sdf.format(c.getTime());  
	}
	
	public static String GetOneWeekBefore(String now, String pattern) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(now));
		c.add(Calendar.DATE, -7);  
		return sdf.format(c.getTime());  
	}
	
}
