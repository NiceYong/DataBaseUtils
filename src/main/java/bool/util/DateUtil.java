/**
 * 
 */
package bool.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author wangw
 */
public class DateUtil {
	/**
	 * 获得当前时间
	 * @return
	 */
	public static Date getCurrentDate() {
		
		
		return new Date();
	}
	
	/**
	 * 获得时间
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		
		return calendar.getTime();
	}
	
	/**
	 * 格式化时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
        if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String ds = sdf.format(date);
			
			return ds;
		}
		
		return null;
	}

	/**
	 * 获取时间
	 *
	 * @param begin
	 * @param end
	 * @param currentTime
	 * @return
	 */
	public static byte getCurrentTiemByte(int begin, int end, String currentTime) {
		byte substring = Byte.parseByte(currentTime.substring(begin, end));

		return substring;
	}
}