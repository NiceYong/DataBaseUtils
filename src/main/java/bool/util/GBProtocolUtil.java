/**
 * 
 */
package bool.util;

import bool.bean.Account;
import bool.bean.Platform;
import bool.config.property.LoginAccountProperties;
import bool.config.property.RedisProperties;
import bool.service.netty.util.ByteBufUtil;
import bool.service.redis.server.RedisServer;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOutboundInvoker;
import org.hibernate.validator.constraints.ModCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * GB协议工具类
 * @author wangw
 */
@Service
public class GBProtocolUtil {
	private static final Logger logger = LoggerFactory.getLogger(GBProtocolUtil.class);


	//关系redis 2
	public static RedisTemplate redisTemplate;
	@Resource(name="redisTemplateRelation")
	public void setRedisTemplateRelation(RedisTemplate<String, String> imeiTemplate) {
		GBProtocolUtil.redisTemplate = imeiTemplate;
	}

	//在线redis  0
	public static RedisTemplate onlineTemplate;
	@Resource(name="redisTemplateOnline")
	public void setRedisTemplateOnline(RedisTemplate<String, String> onlineTemplate) {
		GBProtocolUtil.onlineTemplate = onlineTemplate;
	}

	//流水号redis   3
	public static RedisTemplate flowNumberTemplate;
	@Resource(name="redisTemplateFlowNumber")
	public void setRedisTemplateFlowNumber(RedisTemplate<String, String> flowNumberTemplate) {
		GBProtocolUtil.flowNumberTemplate = flowNumberTemplate;
	}

	//故障redis关系查询   4
	public static RedisTemplate redisFaultQuery;
	@Resource(name="redisFaultQuery")
	public void setRedisFaultQuery(RedisTemplate<String, String> redisFaultQuery) {
		GBProtocolUtil.redisFaultQuery = redisFaultQuery;
	}

	//故障redis推送(单独，实时性较高)   1
	public static RedisTemplate redisFaultRealTtime;
	@Resource(name="redisFaultRealTtime")
	public void setRedisFaultRealTtime(RedisTemplate<String, String> redisFaultRealTtime) {
		GBProtocolUtil.redisFaultRealTtime = redisFaultRealTtime;
	}

	//故障redis推送(单独，实时性较低)   0
	public static RedisTemplate redisFaultAdd;
	@Resource(name="redisFaultAdd")
	public void setRedisFaultAddRedis(RedisTemplate<String, String> redisFaultAdd) {
		GBProtocolUtil.redisFaultAdd = redisFaultAdd;
	}


	//转发vinredis	5
	public static RedisTemplate forwardVINTemplate;
	@Resource(name="redisTemplateForwardVIN")
	public void setRedisTemplateForwardVIN(RedisTemplate<String, String> forwardVINTemplate) {
		GBProtocolUtil.forwardVINTemplate = forwardVINTemplate;
	}

	//平台redis	6
	public static RedisTemplate platformTemplate;
	@Resource(name="redisTemplatePlatform")
	public void setRedisTemplatePlatform(RedisTemplate<String, String> platformTemplate) {
		GBProtocolUtil.platformTemplate = platformTemplate;
	}

	/**
	 * 获得VIN
	 * @param data
	 * @return
	 */
	public static String getVIN(byte[] data) {

		return getVIN(Unpooled.copiedBuffer(data));
	}

	/**
	 * 获得VIN
	 * @param data
	 * @return
	 */
	public static String getVIN(ByteBuf data) {
		ByteBuf vinByteBuf = null;
		try {
			vinByteBuf = data.slice(2, 17);
		}catch(Exception e) {
			logger.warn("数据不完整", e);
			
			return "not_integrated";
		}
		
		byte[] vinBytes = ByteBufUtil.getBytes(vinByteBuf);
		String vin = DataUtil.toASCII(DataUtil.toHexStringArray(vinBytes));
		
		return vin;
	}
	
	/**
	 * 获得IMEI
	 * @param data
	 * @return
	 */
	public static String getIMEI(ByteBuf data) {
		ByteBuf imeiByteBuf = null;
		try {
			imeiByteBuf = data.slice(45, 15);
		}catch(Exception e) {
			logger.warn("数据不完整", e);
			
			return null;
		}
		
		byte[] imeiBytes = ByteBufUtil.getBytes(imeiByteBuf);
		String imei = DataUtil.toASCII(DataUtil.toHexStringArray(imeiBytes));
		imei = imei.substring(1, imei.length());//截掉一位
		
		return imei;
	}

	/***
	 * 获得报文时间
	 * @param bytes
	 * @return
	 */
	public static Date getMessageTime(byte[] bytes, int start){

		int year =DataUtil.toInt(bytes[start])+2000;
		int month=DataUtil.toInt(bytes[++start]);
		int day=DataUtil.toInt(bytes[++start]);
		int hour=DataUtil.toInt(bytes[++start]);
		int minute=DataUtil.toInt(bytes[++start]);
		int second=DataUtil.toInt(bytes[++start]);
		return DateUtil.getDate(year, month - 1, day, hour, minute, second);

	}
	
	/**
	 * 查询IMEI的信息
	 * @param imei
	 * @return
	 */
	private static Map<String, String> getIMEIMap(String imei) {
		
		try {
			Map<String, String> imeiMap = redisTemplate.opsForHash().entries(imei);
			
			if(imeiMap == null) {
				logger.warn("不存在此IMEI的信息：" + imei);
			}
			
			return imeiMap;
		}catch(Exception e) {
			logger.error("查询IMEI的信息异常");
			
			return null;
		}
	}

	/**
	 * 查询对应vin的故障阈值
	 * @param vin
	 * @return
	 */
	public static Map<String, String> getFaultMap(String vin) {

		try {
			Map<String, String> faultMap = redisFaultQuery.opsForHash().entries(vin);

			if(faultMap == null) {
				logger.warn("不存在此VIN的信息：" + vin);
			}
			return faultMap;
		}catch(Exception e) {
			logger.error("查询VIN的信息异常" + e.toString() + "//"+ vin);

			return null;
		}
	}


	/*
	* 推送故障数据 0
	* 实时性较低
	* */
	public static void redisPush(Map<String,String>faultMap){
		if(faultMap == null){
			logger.error("故障Map为空");
			return;
		}
		try {
			String json = JSON.toJSONString(faultMap,true);
			//推送方式
			//redisFaultAdd.convertAndSend("bool",json);
			//队列方式
			redisFaultAdd.opsForList().leftPush("bool",json);
		}catch (Exception e){
			logger.error("存储故障json失败",e.toString());
		}
	}

	/**
	 * 推送故障数据 1
	 * 实时性较高
	 * 增加或者覆盖 ： key：vin value：json
	 * */
	public static void saveFaul(String vin,Map<String,String>faultMap){
		try {
			String json = JSON.toJSONString(faultMap,true);
			redisFaultRealTtime.opsForValue().set(vin,json);
		} catch (Exception e) {
			logger.error("故障实时性存储失败:",e.toString() + "//"+ vin);
		}
	}

	/**
	 * 删除没有故障vin列表 1
	 * 保证实时性
	 *
	 * */
	public static void deleteFaul(String vin){
		try {
			 redisFaultRealTtime.delete(vin);
		} catch (Exception e) {
			logger.error("删除vin失败:",e.toString() + "//"+ vin);
		}
	}

	/**
	 * 查询名称对应的转发平台信息
	 * @param Name
	 * @return
	 */
	private static Map<String, String> getPlatformMap(String Name) {
		try {
			Map<String, String> platformMap = platformTemplate.opsForHash().entries(Name);
			if(platformMap == null) {
				logger.warn("不存在此名称的平台信息：" + platformMap);
			}
			return platformMap;
		}catch(Exception e) {
			logger.error("查询平台信息异常");
			return null;
		}
	}
	/**
	 * 查询名称对应的转发平台信息
	 * @param vin
	 * @return
	 */
	private static Set<String> getPlatformList(String vin) {
		try {
			Set<String> platformList= forwardVINTemplate.opsForSet().members(vin);
			if(platformList == null) {
				logger.warn("不存在此名称的转发vin信息：" + platformList);
			}
			return platformList;
		}catch(Exception e) {
			logger.error("查询转发vin信息异常");
			return null;
		}
	}
	
	/**
	 * 查询VIN
	 * @param imei
	 * @return
	 */
	public static String getVIN(String imei) {
		Map<String, String> imeiMap = getIMEIMap(imei);
		if(imeiMap==null || imeiMap.size()==0) {
			return null;
		}
		
		String vin = imeiMap.get("VIN");
		if("".equals(vin) || vin==null) {
			logger.warn("此imei对应的VIN为空值：" + imei);
		}
		
		return vin;
	}

	/**
	 * 查询协议名称
	 * @param imei
	 * @return
	 */
	public static String getProtocolName(String imei) {
		Map<String, String> imeiMap = getIMEIMap(imei);
//		imeiMap == null 改为 imeiMap.size() == 0 yangsibao
		if (imeiMap==null || imeiMap.size()==0) {
			return null;
		}
		
		String protocolName = imeiMap.get("xmlName");
		if("".equals(protocolName) || protocolName==null) {
			logger.warn("此imei对应的协议名称为空值：" + imei);
		}
		
		return protocolName;
	}
	/**
	 * 查询转发vin
	 * @param vin
	 * @return
	 */
	private static Set<String> getForwardVIN(String vin) {
		Set<String> vinPlatform = getPlatformList(vin);
//		imeiMap == null 改为 imeiMap.size() == 0 yangsibao
		if (vinPlatform==null || vinPlatform.size()==0) {
			return null;
		}
		return vinPlatform;
	}
	/**
	 * 查询平台相关信息
	 * @param platformName
	 * @return
	 */
	private static Platform getPlatform(String platformName) {
		Map<String, String> platformMap = getPlatformMap(platformName);
//		imeiMap == null 改为 imeiMap.size() == 0 yangsibao
		if (platformMap==null || platformMap.size()==0) {
			return null;
		}
		for (String value : platformMap.values()) {
			if(value==null||value.length()==0){
				logger.warn("此名称对应的平台有空值：" + platformName);
				return null;
			}
		}
		Platform platform = new Platform();
		platform.setHost(platformMap.get("host"));
		platform.setPort(Integer.parseInt(platformMap.get("port")));
		platform.setUserName(platformMap.get("userName"));
		platform.setPassword(platformMap.get("password"));
		return platform;
	}

	/***
	 * 查询VIN需要转发的平台
	 * @param vin
	 * @return
	 */



	public static List<Platform> getVINPlatform (String vin){
		List<Platform> platformList=new ArrayList<>();
		Set<String> platformName= getForwardVIN(vin);
		if(platformName == null||platformName.size() == 0){
			return null;
		}
		for (String name:platformName) {
			Platform platform=getPlatform(name);
			if(platform!=null) {
				platformList.add(platform);
			}
		}
		return platformList;
	}



	/**
	 * 查询ICCID
	 * @param imei
	 * @return
	 */
	public static String getICCID(String imei) {
		Map<String, String> imeiMap = getIMEIMap(imei);
		if(imeiMap==null || imeiMap.size()==0) {
			return null;
		}
		
		String iccid = imeiMap.get("ICCID");
		if("".equals(iccid) || iccid==null) {
			logger.warn("此imei对应的ICCID为空值：" + imei);
		}
		
		return iccid;
	}
	
	/**
	 * 查询公司的英文名称
	 * @param imei
	 * @return
	 */
	public static String getCompanyEnglishName(String imei) {
		Map<String, String> imeiMap = getIMEIMap(imei);
		if(imeiMap==null || imeiMap.size()==0) {
			return null;
		}
		
		String companyEnglishName = imeiMap.get("companyEnglishName");
		if("".equals(companyEnglishName) || companyEnglishName==null) {
			logger.warn("此imei对应的公司英文名称为空值：" + imei);
		}
		
		return companyEnglishName;
	}

	/**
	 * 获得平台登陆的流水号
	 * @param ip
	 * @return
	 */
	private static int getPlatformLoginNumber(String ip) {

		try {
			long loginNumber = flowNumberTemplate.opsForHash().increment("platform_login", ip, 1);
			if(loginNumber > 65531) {
				loginNumber = 1;

				flowNumberTemplate.opsForHash().put("platform_login", ip, String.valueOf(loginNumber));
			}

			return (int) loginNumber;
		}catch(Exception e) {
			logger.error("获得平台登陆的流水号异常" + e.toString());

			return 0;
		}
	}

	/**
	 * 获得上次平台登陆的流水号
	 * @param ip
	 * @return
	 */
	private static int getLastPlatformLoginNumber(String ip) {

		try {
			String loginNumberString = (String) flowNumberTemplate.opsForHash().get("platform_login", ip);

			int loginNumber = 0;
			if(!"".equals(loginNumberString) && loginNumberString!=null) {
				loginNumber = Integer.valueOf(loginNumberString).intValue();
			}

			return loginNumber;
		}catch(Exception e) {
			logger.error("获得平台登陆的流水号异常", e.toString());

			return 0;
		}
	}

	/**
	 * 获得平台登陆的密钥
	 * @param ip
	 * @param loginAccount
	 * @return
	 */
	private static byte[] getPlatformLoginKey(String ip, Account loginAccount) {
		byte[] loginKey = new byte[64];
//		loginKey[0] = 0x23;	//起始符
//		loginKey[1] = 0x23;	//起始符
		loginKey[0] = 0x05;	//命令标识
		loginKey[1] = (byte) 0xFE;	//应答标志

		//唯一识别码
		loginKey[2] = 0X4C;
		loginKey[3] = 0X4A;
		loginKey[4] = 0X38;
		loginKey[5] = 0X35;
		loginKey[6] = 0X33;
		loginKey[7] = 0X41;
		loginKey[8] = 0X35;
		loginKey[9] = 0X4D;
		loginKey[10] = 0X39;
		loginKey[11] = 0X46;
		loginKey[12] = 0X30;
		loginKey[13] = 0X35;
		loginKey[14] = 0X30;
		loginKey[15] = 0X33;
		loginKey[16] = 0X33;
		loginKey[17] = 0X31;
		loginKey[18] = 0X39;

		loginKey[19] = 0x01;	//数据单元加密方式

		//数据单元长度
		loginKey[20] = 0x00;
		loginKey[21] = 0x29;

		//平台登入时间
		Calendar calendar = Calendar.getInstance();
		loginKey[22] = (byte) (calendar.get(Calendar.YEAR)%1000);
		loginKey[23] = (byte) (calendar.get(Calendar.MONTH)+1);
		loginKey[24] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
		loginKey[25] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
		loginKey[26] = (byte) calendar.get(Calendar.MINUTE);
		loginKey[27] = (byte) calendar.get(Calendar.SECOND);

		//登入流水号
		int loginNumber = getPlatformLoginNumber(ip);
		byte[] loginNumberBytes = DataUtil.toByteArray(DataUtil.toHexString(loginNumber));
		loginKey[28] = loginNumberBytes.length==2?loginNumberBytes[0]:0x00;
		loginKey[29] = loginNumberBytes.length==2?loginNumberBytes[1]:loginNumberBytes[0];

		//平台用户名
		String userName = loginAccount==null?null:loginAccount.getUserName();

		byte[] userNameBytes= new byte[12];
		byte[] loginNameBytes;
		if("".equals(userName) || userName==null) {
			loginNameBytes = new byte[12];

			logger.warn("平台用户名为" + userName + " @" + ip);
		}else {
			loginNameBytes = userName.getBytes();
		}

		for(int i=0;i<(loginNameBytes.length>12?12:loginNameBytes.length);i++){

			userNameBytes[i]=loginNameBytes[i];
		}

		loginKey[30] = userNameBytes[0];
		loginKey[31] = userNameBytes[1];
		loginKey[32] = userNameBytes[2];
		loginKey[33] = userNameBytes[3];
		loginKey[34] = userNameBytes[4];
		loginKey[35] = userNameBytes[5];
		loginKey[36] = userNameBytes[6];
		loginKey[37] = userNameBytes[7];
		loginKey[38] = userNameBytes[8];
		loginKey[39] = userNameBytes[9];
		loginKey[40] = userNameBytes[10];
		loginKey[41] = userNameBytes[11];

		//平台密码
		String password = loginAccount==null?null:loginAccount.getPassword();

		byte[] passwordBytes=new byte[20];
		byte[] loginpswBytes;
		if("".equals(password) || password==null) {

			loginpswBytes = new byte[20];

			logger.warn("平台密码为" + password + " @" + ip);
		}else {
			loginpswBytes = password.getBytes();
		}

		for(int i=0;i<(loginpswBytes.length>20?20:loginpswBytes.length);i++){

			passwordBytes[i]=loginpswBytes[i];

		}

		loginKey[42] = passwordBytes[0];
		loginKey[43] = passwordBytes[1];
		loginKey[44] = passwordBytes[2];
		loginKey[45] = passwordBytes[3];
		loginKey[46] = passwordBytes[4];
		loginKey[47] = passwordBytes[5];
		loginKey[48] = passwordBytes[6];
		loginKey[49] = passwordBytes[7];
		loginKey[50] = passwordBytes[8];
		loginKey[51] = passwordBytes[9];
		loginKey[52] = passwordBytes[10];
		loginKey[53] = passwordBytes[11];
		loginKey[54] = passwordBytes[12];
		loginKey[55] = passwordBytes[13];
		loginKey[56] = passwordBytes[14];
		loginKey[57] = passwordBytes[15];
		loginKey[58] = passwordBytes[16];
		loginKey[59] = passwordBytes[17];
		loginKey[60] = passwordBytes[18];
		loginKey[61] = passwordBytes[19];

		loginKey[62] = 0x01;	//加密规则
		loginKey[63] = DataUtil.toBCC(loginKey, 0, loginKey.length-2);	//校验码

		return loginKey;
	}

	/**
	 * 获得平台登出的密钥
	 * @param ip
	 * @return
	 */
	private static byte[] getPlatformLogoutKey(String ip) {
		byte[] logoutKey = new byte[31];
//		logoutKey[0] = 0x23;	//起始符
//		logoutKey[1] = 0x23;	//起始符
		logoutKey[0] = 0x06;	//命令标识
		logoutKey[1] = (byte) 0xFE;	//应答标志

		//唯一识别码
		logoutKey[2] = 0X4C;
		logoutKey[3] = 0X4A;
		logoutKey[4] = 0X38;
		logoutKey[5] = 0X35;
		logoutKey[6] = 0X33;
		logoutKey[7] = 0X41;
		logoutKey[8] = 0X35;
		logoutKey[9] = 0X4D;
		logoutKey[10] = 0X39;
		logoutKey[11] = 0X46;
		logoutKey[12] = 0X30;
		logoutKey[13] = 0X35;
		logoutKey[14] = 0X30;
		logoutKey[15] = 0X33;
		logoutKey[16] = 0X33;
		logoutKey[17] = 0X31;
		logoutKey[18] = 0X39;

		logoutKey[19] = 0x01;	//数据单元加密方式

		//数据单元长度
		logoutKey[20] = 0x00;
		logoutKey[21] = 0x08;

		//登出时间
		Calendar calendar = Calendar.getInstance();
		logoutKey[22] = (byte) (calendar.get(Calendar.YEAR)%1000);
		logoutKey[23] = (byte) (calendar.get(Calendar.MONTH)+1);
		logoutKey[24] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
		logoutKey[25] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
		logoutKey[26] = (byte) calendar.get(Calendar.MINUTE);
		logoutKey[27] = (byte) calendar.get(Calendar.SECOND);

		//登出流水号
		int logoutNumber = getLastPlatformLoginNumber(ip);
		byte[] logoutNumberBytes = DataUtil.toByteArray(DataUtil.toHexString(logoutNumber));
		logoutKey[28] = logoutNumberBytes.length==2?logoutNumberBytes[0]:0x00;
		logoutKey[29] = logoutNumberBytes.length==2?logoutNumberBytes[1]:logoutNumberBytes[0];

		logoutKey[30] = DataUtil.toBCC(logoutKey, 0, logoutKey.length-2);	//校验码

		return logoutKey;
	}

	/**
	 * 平台登陆
	 * @param out
	 * @param ip
	 * @param loginAccount
	 */
	public static void platformLogin(ChannelOutboundInvoker out, String ip, Account loginAccount) {
		logger.info("登陆目标平台 @" + ip);

		byte[] loginKey = getPlatformLoginKey(ip, loginAccount);

		logger.info(DataUtil.join(DataUtil.toHexStringArray(loginKey)));

		ChannelFuture writeChannelFuture = out.writeAndFlush(Unpooled.copiedBuffer(loginKey));
		writeChannelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()) {
					logger.info("登陆目标平台的密钥发送成功 @" + ip);
				}else {
					logger.error("登陆目标平台的密钥发送失败 @" + ip);
				}
			}
		});
	}

	/**
	 * 是否平台登陆信息
	 * @param data
	 * @return
	 */
	public static boolean isPlatformLoginMessage(String[] data) {
		if("05".equals(data[0])) {
			return true;
		}

		return false;
	}

	/**
	 * 是否平台登陆成功
	 * @param data
	 * @return
	 */
	public static boolean isPlatformLoginSuccessfully(String[] data) {
		if("01".equals(data[1])) {
			return true;
		}

		return false;
	}

	/**
	 * 平台登出
	 * @param out
	 * @param ip
	 */
	public static void platformLogout(ChannelOutboundInvoker out, String ip) {
		logger.info("登出目标平台 @" + ip);

		byte[] logoutKey = getPlatformLogoutKey(ip);

		logger.info(DataUtil.join(DataUtil.toHexStringArray(logoutKey)));

		ChannelFuture writeChannelFuture = out.writeAndFlush(Unpooled.copiedBuffer(logoutKey));
		writeChannelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()) {
					logger.info("登出目标平台的密钥发送成功 @" + ip);
				}else {
					logger.error("登出目标平台的密钥发送失败 @" + ip);
				}
			}
		});
	}

	/**
	 * 是否平台登出信息
	 * @param data
	 * @return
	 */
	public static boolean isPlatformLogoutMessage(String[] data) {
		if("06".equals(data[0])) {
			return true;
		}

		return false;
	}

	/**
	 * 是否平台登出成功
	 * @param data
	 * @return
	 */
	public static boolean isPlatformLogoutSuccessfully(String[] data) {
		if("01".equals(data[1])) {
			return true;
		}

		return false;
	}

	/**
	 * 是否是实时信息上报
	 * @param data
	 * @return
	 */
	public static boolean isRealTimeReport(String[] data) {
		if("02".equals(data[0])) {
			return true;
		}

		return false;
	}

	/**
	 * 是否是补发信息上报
	 * @param data
	 * @return
	 */
	public static boolean isReissueReport(String[] data) {
		if("03".equals(data[0])) {
			return true;
		}

		return false;
	}

	/**
	 * 是否是正确的实时信息上报
	 * @param data
	 * @return
	 */
	public static boolean isMessageSuccessAnswer(String[] data) {
		if("01".equals(data[1])) {
			return true;
		}

		return false;
	}

	/**
	 * 是否是错误的实时信息上报
	 * @param data
	 * @return
	 */
	public static boolean isMessageFailAnswer(String[] data) {
		if("02".equals(data[1])) {
			return true;
		}

		return false;
	}

	/**
	 * 查询登陆账号
	 * @param imei
	 * @param
	 * @return
	 */
	public static Account getLoginAccount(String imei, LoginAccountProperties loginAccountProperties) {
		Account loginAccount = loginAccountProperties.getLoginAccountMap().get(getCompanyEnglishName(imei));

		if(loginAccount == null) {
			logger.warn("IMEI“" + imei + "”对应的登陆账号不存在");
		}

		return loginAccount;
	}
}