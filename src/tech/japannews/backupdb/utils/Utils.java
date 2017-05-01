package tech.japannews.backupdb.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Utils {
	public static String formatTime(LocalDateTime now) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(Const.DATE_PATTERN_YMD);
		return now.format(format);
	}

}
