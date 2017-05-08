package tech.japannews.backupdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import tech.japannews.backupdb.utils.Const;
import tech.japannews.backupdb.utils.Utils;

public class ProcessBackup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			String results = BackupMessage("message");
			if(results.contains("Successfully")){
				String truncateResult = TruncateMessage("message");
				System.out.println(truncateResult);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String BackupMessage(String tableName) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(Const.BACKUP_DB);
		
		Map<String,Object> params = new LinkedHashMap<>();
		String today_yyyMMdd = (String)Utils.formatTime(LocalDateTime.now(), Const.DATE_PATTERN_YMD);
		String today_yyyy_MM_dd = Utils.formatTime(LocalDateTime.now(), Const.DATE_PATTERN_Y_M_D);
			
		String week_before = "";
		try {
			week_before = Utils.GetOneWeekBefore(today_yyyy_MM_dd, Const.DATE_PATTERN_Y_M_D);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        params.put("date", today_yyyMMdd);
        params.put("message", tableName);
        params.put("week_before", week_before);
        System.out.println("date = " + today_yyyMMdd);
        System.out.println("week_before = " + week_before);
  
        return PostToServer(url,params);
 
	}
	
	
	private static String TruncateMessage(String tableName) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(Const.TRUNCATE_DB);
		
		Map<String,Object> params = new LinkedHashMap<>();
		String today_yyyy_MM_dd = Utils.formatTime(LocalDateTime.now(), Const.DATE_PATTERN_Y_M_D);
		
		String week_before = "";
		try {
			week_before = Utils.GetOneWeekBefore(today_yyyy_MM_dd, Const.DATE_PATTERN_Y_M_D);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        params.put("message", tableName);
        params.put("week_before", week_before);
  
        return PostToServer(url,params);

	}
	
	
	private static String PostToServer(URL url, Map<String,Object> params) throws IOException{
		StringBuilder builder = new StringBuilder();
		
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String line;
        while ((line= in.readLine()) != null){
        	builder.append(line);
        }
        
        return builder.toString();
    
	}

}
