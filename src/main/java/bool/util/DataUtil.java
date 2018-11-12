/**
 * 
 */
package bool.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据工具类
 */
public class DataUtil {
	private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

	private static final String[] BINARY_STRING_ARRAY =
		{"0000", "0001", "0010", "0011",
		"0100", "0101", "0110", "0111",
		"1000", "1001", "1010", "1011",
		"1100", "1101", "1110", "1111"};


	/***
	 * 将（-128~127范围）byte转换为（0~255）int
	 * @param b
	 * @return
	 */
	public  static  int toInt(byte b){
		int n;
		if(b>=0){

			n=b;
		}else{

			n=b+256;
		}
		return  n;
	}

	/***
	 * 将byte数组转换为int数组
	 * @param bytes
	 * @return
	 */
	public  static  int[] toIntArray(byte[] bytes){

		int[] ints =new int[bytes.length];
		for(int i=0;i<bytes.length;i++){

			ints[i]=toInt(bytes[i]);


		}
		return  ints;
	}

	/**
	 *  unicode转gbk
	 * @param dataStr
	 * @return String
	 */

	public static String UnicodeToGBK(String dataStr) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();

		int li_len = dataStr.length();
		while (index < li_len) {
			if (index >= li_len - 1
					|| !"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));

				index++;
				continue;
			}

			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);

			char letter = (char) Integer.parseInt(charStr, 16);

			buffer.append(letter);
			index += 6;
		}

		return buffer.toString();
	}


	/**
	 * 将16进制字符串转换成8421码
	 * @param data
	 * @return
	 */
	public static String toDecStr(String data) {
		if (StringUtils.isEmpty(data)) {
			return "无列表";
		}
		List<String> list = getStrList(data,4);

		String str="";
		//遍历得到4位一组二进制的string
		for(int i = 0, size = list.size(); i < size; i++){
			String str0 = list.get(i);
			//将二进制字符串转换成十进制的数
			int num=Integer.parseInt(str0,2);
			//8421转码格式要求经转码后10进制数不能大于等于10
			if(num>=10){
				return "not_integrated";

			}else{
				//在原str后添加num valueOf将num转换成字符串
				//相当于str = str+num
				str+=String.valueOf(num);
			}
		}
		return str;
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 *
	 * @param inputString
	 *            原始字符串
	 * @param length
	 *            指定长度
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length) {
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		return getStrList(inputString, length, size);
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 *
	 * @param inputString
	 *            原始字符串
	 * @param length
	 *            指定长度
	 * @param size
	 *            指定列表大小
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length, int size) {
		List<String> list = new ArrayList<String>();
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length,
					(index + 1) * length);
			list.add(childStr);
		}
		return list;
	}

	/**
	 * 分割字符串，如果开始位置大于字符串长度，返回空
	 *
	 * @param str
	 *            原始字符串
	 * @param f
	 *            开始位置
	 * @param t
	 *            结束位置
	 * @return
	 */
	public static String substring(String str, int f, int t) {
		if (f > str.length())
			return null;
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}

	/***
	 * 把byte转为字符串的bit
	 * @param
	 * @return
	 */
	public static String byteToBit(byte b) {
		return ""
				+ (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}



	/**
	 * 将字节转换为16进制字符串
	 * @param b
	 * @return
	 */
	public static String toHexString(byte b) {
        String hexString = Integer.toHexString(b & 0xFF);
		
        if (hexString.length() == 1) {
			hexString = '0' + hexString;
		}
		
		return hexString;
	}
	
	/**
	 * 将字节数组转换为16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes) {
		String[] hexStringArray = toHexStringArray(bytes);
		
		return Arrays.toString(hexStringArray);
	}
	
	/**
	 * 将整数转换为16进制字符串
	 * @param number
	 * @return
	 */
	public static String toHexString(int number) {
		String numberHexString = Integer.toHexString(number);
		if(numberHexString.length()%2 != 0) {
			numberHexString = '0' + numberHexString;
		}

		return numberHexString;
	}

	/**
	 * 将字节数组转换为16进制字符串数组
	 * @param bytes
	 * @return
	 */
	public static String[] toHexStringArray(byte[] bytes) {
		int bytesLength = bytes.length;
		String[] hexStringArray = new String[bytesLength];
		
        for (int i = 0; i < bytesLength; i++) {
			hexStringArray[i] = toHexString(bytes[i]);
		}
		
		return hexStringArray;
	}
	
	/**
	 * 将字节数组转换为2进制字符串
	 * @param bytes
	 * @return
	 */
	public static String toBinaryString(byte[] bytes) {
		StringBuffer binStrBuf = new StringBuffer();
		
		int pos = 0;
        for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			
            pos = b & 0xF0 >> 4;
			binStrBuf.append(BINARY_STRING_ARRAY[pos]);
			
            pos = b & 0x0F;
			binStrBuf.append(BINARY_STRING_ARRAY[pos]);
		}
		
		return binStrBuf.toString();
	}
	
	/**
	 * 将16进制字符串数组转换为2进制字符串
	 * @param data
	 * @return
	 */
	public static String toBinaryString(String[] data) {
		String dataStr = join(data);
		byte[] byteArray = toByteArray(dataStr);
		String binStr = toBinaryString(byteArray);
		
		return binStr;
	}
	
	/**
	 * 将16进制字符串数组转换为2进制字符数组
	 * @param data
	 * @return
	 */
	public static char[] toBinaryArray(String[] data) {
		String binStr = toBinaryString(data);
		
		return binStr.toCharArray();
	}
	
	/**
	 * 异或校验
	 * @param data
	 * @return
	 */
	public static byte toBCC(byte[] data) {
		byte code = 0x00;
		
		int dataLength = data.length;
        if (dataLength > 0) {
            code = toBCC(data, 0, data.length - 1);
		}
		
		return code;
	}
	
	/**
	 * 异或校验
	 * @param data
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static byte toBCC(byte[] data, int beginIndex, int endIndex) {
		byte code = data[beginIndex];
		
        for (int i = beginIndex + 1; i <= endIndex; i++) {
			code ^= data[i];
		}
		
		return code;
	}
	
	/**
	 * 将字节转换为ASCII码
	 * @param b
	 * @return
	 */
	public static char toASCII(byte b) {
        char c;
        if (b == 0) {//为了防止为空yangsibao
            c = '0';
        } else {
            c = (char) b;
        }
		
		return c;
	}
	
	/**
	 * 将字节数组转换为ASCII码数组
	 * @param data
	 * @return
	 */
	public static char[] toASCII(byte[] data) {
		int dataLength = data.length;
		char[] ascii = new char[dataLength];
		
        for (int i = 0; i < dataLength; i++) {
			ascii[i] = toASCII(data[i]);
		}
		
		return ascii;
	}
	
	/**
	 * 将16进制字符串数组转换为ASCII码
	 * @param data
	 * @return
	 */
	public static String toASCII(String[] data) {
		StringBuffer asciiBuf = new StringBuffer();
		
		String str = join(data);
		byte[] bytes = toByteArray(str);
        for (int i = 0; i < bytes.length; i++) {
			asciiBuf.append(toASCII(bytes[i]));
		}
		
		return asciiBuf.toString();
	}
	
	/**
	 * 将16进制字符转换为字节
	 * @param hexChar
	 * @return
	 */
	public static byte toByte(char hexChar) {
		byte b = (byte) "0123456789ABCDEF".indexOf(String.valueOf(hexChar).toUpperCase());
		
		return b;
	}
	

	/**
	 * 将16进制字符串转换为字节数组
	 * @param hexString
	 * @return
	 */
	public static byte[] toByteArray(String hexString) {
		int len = hexString.length() / 2;
		byte[] byteArray = new byte[len];
		char[] charArray = hexString.toCharArray();
		
        for (int i = 0; i < len; i++) {
			int pos = i * 2;
			
            byteArray[i] = (byte) (toByte(charArray[pos]) << 4 | toByte(charArray[pos + 1]));
		}
		
		return byteArray;
	}
	
	/**
	 * 将字符串列表转换为字符串数组
	 * @param list
	 * @return
	 */
	public static String[] toStringArray(List<String> list) {
		String[] array = new String[list.size()];
		list.toArray(array);
		
		return array;
	}
	
	/**
	 * 将字符串转换为有符号的整数
	 * @param data
	 * @param radix
	 * @return
	 */
	public static int toInt(String data, int radix) {
		int n = Integer.parseInt(data, radix);
		
		return n;
	}

	/**
	 * 将字符串数组转换为有符号的整数
	 * @param data
	 * @param radix
	 * @return
	 */
	public static int toInt(String[] data, int radix) {
		StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
			strBuf.append(data[i]);
		}
		
		int n = Integer.parseInt(strBuf.toString(), radix);
		
		return n;
	}
	
	/**
	 * 是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
        if (str == null) {
			return false;
		}
		int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 将字符串数组合并为一个字符串
	 * @param data
	 * @return
	 */
	public static String join(String[] data) {
		StringBuffer strBuf = new StringBuffer();
		
        for (int i = 0; i < data.length; i++) {
			strBuf.append(data[i]);
		}
		
		return strBuf.toString();
	}
	
	/**
	 * 将对象转换为JSON字符串
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		String jsonString = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
		
		return jsonString;
	}

	/**
	 * 将字符串转换为有符号的长整数
	 * 为了解决累计里程过长的问题
	 *
	 * @param data
	 * @param radix
	 * @return
	 * @Author yangsibao
	 */
	public static long toLong(String data, int radix) {
		long l = Long.parseLong(data, radix);

		return l;
	}

	/**
	 * 数组倒序
	 *
	 * @param array
	 * @return
	 * @Author yangsibao
	 */
	public static String[] reverseStrArray(String[] array) {
		int k = array.length;
		int j = k;
		String[] result = new String[array.length];
		for (int i = 0; i < k; i++) {
			result[i] = array[j - 1];
			j--;
		}
		return result;
	}

	/**
	 * 从读取的报文中获取目标字节，并将目标字节转换为二进制字符串，并截取二进制字符串中想要的子字符串
	 *
	 * @param encodeInfoArray 读取的报文字符串数组
	 * @param startbit        截取的二进制的起始索引，在标签中
	 * @param bitlength       截取的二进制的
	 * @return 解析为十进制的字符串
	 * @Author yangsibao
	 */
	public static String cutBinStrFromStrArr(String[] encodeInfoArray, int startbit, int bitlength) {
		String targetByte = null;
		if (encodeInfoArray.length == 1) {
			targetByte = encodeInfoArray[0];//如果读到的字节为一个长度，那么直接读取
		} else {
			targetByte = join(encodeInfoArray);//如果长度大于1，拼为一个字符串
		}

        String binaryStr = DataUtil.hexStrToBinaryStr(targetByte);//将目标字节转换为二进制字符串

		int begin = binaryStr.length() - startbit - bitlength;//目前应该只支持8位二进制
		int end = begin + bitlength;

        String targetNum = binaryStr.substring(begin, end);//截取二进制字符串中想要的子字符串
        Integer integer = Integer.valueOf(targetNum, 2);//将截取到的二进制字符串解析为十进制数字
        String result = integer.toString();

		return result;
	}

	/**
	 * 将16进制字符串转换成二进制字符串
	 *
	 * @param hexString 十六进制字符串
	 * @return
	 * @Author yangsibao
	 */
	public static String hexStrToBinaryStr(String hexString) {

		if (hexString == null || hexString.equals("")) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// 将每一个十六进制字符分别转换成一个四位的二进制字符
		for (int i = 0; i < hexString.length(); i++) {
			String indexStr = hexString.substring(i, i + 1);
			String binaryStr = Integer.toBinaryString(Integer.parseInt(indexStr, 16));
			while (binaryStr.length() < 4) {
				binaryStr = "0" + binaryStr;
			}
			sb.append(binaryStr);
		}

		return sb.toString();
	}

	/**
	 * 将目标数字进行范围判断，并将结果写进result和data
	 *
	 * @param comparedNum 被比对的字符串
	 * @param min         最小值
	 * @param max         最大值
	 * @Author yangsibao
	 */
	public static boolean judgeRange(String comparedNum, int min, int max) {
		BigDecimal targetNum = new BigDecimal(comparedNum);
		BigDecimal smin = new BigDecimal(min);
		BigDecimal smax = new BigDecimal(max);

		if (targetNum.compareTo(smin) >= 0 && targetNum.compareTo(smax) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 十六进制字符串数组转十进制字符串
	 *
	 * @param endecodedata
	 * @return
	 */
	public static String toDecStr(String[] endecodedata) {
		String decNum = join(endecodedata);
		if (StringUtils.isEmpty(decNum)) {
			return "无列表";
		}
		//Integer integer = Integer.parseInt(decNum, 16);
		Long longNum = Long.parseLong(decNum, 16);
		return longNum.toString();
	}

	/**
	 * 将数据转为时间
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Date convertToDate(Map<String, Object> date) {
		Map<String, Object> dateMap = (Map<String, Object>) date.get("DATETIME");
		if (date == null || date.size() == 0) {
			return null;
		}
		//先转字符串，解决空值问题
		String yearStr = dateMap.get("YEAR").toString().replaceAll("[^\\d]", "");
		String monthStr = dateMap.get("MONTH").toString().replaceAll("[^\\d]", "");
		String dayStr = dateMap.get("DAY").toString().replaceAll("[^\\d]", "");
		String hourStr = dateMap.get("HOUR").toString().replaceAll("[^\\d]", "");
		String minuteStr = dateMap.get("MINUTE").toString().replaceAll("[^\\d]", "");
		String secondStr = dateMap.get("SECOND").toString().replaceAll("[^\\d]", "");


		if (Integer.valueOf(yearStr).intValue() <= 9) {
			yearStr = '0' + yearStr;
		}

		int year = Integer.valueOf("20" + yearStr).intValue();
		int month = Integer.valueOf(monthStr).intValue();
		int day = Integer.valueOf(dayStr).intValue();
		int hour = Integer.valueOf(hourStr).intValue();
		int minute = Integer.valueOf(minuteStr).intValue();
		int second = Integer.valueOf(secondStr).intValue();
		//获取当前年，若解析出的年超过当前年时，赋当前时间
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);

		if (year == 2000 && month == 0 && day == 0 && hour == 0 && minute == 0 && second == 0) {
			SimpleDateFormat myFmt =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.000");
			String time = myFmt.format(new Date());
			SimpleDateFormat myFmt2 =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
			try {
				Date accurate = myFmt2.parse(time);
				return accurate;
			} catch (ParseException e) {
					logger.error("时间精确到毫秒失败" + e.toString());
			}
		}else if(year > nowYear || month > 12 || day > 31 || minute > 60 || second > 60){
			SimpleDateFormat myFmt =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.000");
			String time = myFmt.format(new Date());
			SimpleDateFormat myFmt2 =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
			try {
				Date accurate = myFmt2.parse(time);
				return accurate;
			} catch (ParseException e) {
				logger.error("时间精确到毫秒失败" + e.toString());
			}
		}
		return DateUtil.getDate(year, month - 1, day, hour, minute, second);
	}

	/**
	 * 数组指定位置添加元素
	 * @param a         初始数组
	 * @param index     下标
	 * @param n         要加入的元素
	 * @return
	 */
	public static String[] addArray(String[] a,int index,String n){
		String []a1=new String[a.length+1];//定义一个新的整型数组且长度在原数组长度上+1

		for(int i=0;i<index;i++){

			a1[i]=a[i];//把要插入位置之前的元素装进新数组

		}

		a1[index]=n;//插入位置的元素为n

		for(int i=index+1;i<a1.length;i++){

			a1[i]=a[i-1];//把插入位置之后的元素装进新数组

		}
		return a1;
	}

}
