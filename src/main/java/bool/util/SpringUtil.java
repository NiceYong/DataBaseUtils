/**
 * 
 */
package bool.util;

import org.springframework.context.ApplicationContext;

/**
 * Spring工具类
 * @author wangw
 */
public class SpringUtil {
	private static ApplicationContext appContext;
	
	/**
	 * 设置应用上下文
	 * @param applicationContext
	 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		appContext = applicationContext;
	}
	
	/**
	 * 获得应用上下文
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		
		
		return appContext;
	}
	
	/**
	 * 获得Bean
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		if(appContext != null) {
			return appContext.getBean(name);
		}
		
		return null;
	}
	
	/**
	 * 获得Bean
	 * @param type
	 * @return
	 */
	public static Object getBean(Class<?> type) {
		if(appContext != null) {
			return appContext.getBean(type);
		}
		
		return null;
	}
}