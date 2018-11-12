/**
 * 
 */
package bool.config.property;

import bool.bean.Account;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 登陆属性
 * @author wangw
 */
@Data
@Component
@ConfigurationProperties(prefix="loginaccount")
public class LoginAccountProperties {
	/**
	 * 登陆账号Map
	 */
	private Map<String, Account> loginAccountMap;

}