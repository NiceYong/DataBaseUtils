/**
 *
 */
package bool.service.redis.server;

import bool.config.property.FaultRedisProperties;
import bool.config.property.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis服务器
 * @author wangw
 */
@Configuration
public class RedisServer {
    private static final Logger logger = LoggerFactory.getLogger(RedisServer.class);

    @Autowired
    private RedisProperties boolRedisProperties;

    @Autowired
    private FaultRedisProperties faultRedisProperties;

    /**
     * 关系redis redisTemplate
     * @param
     * @return
     */

    @Bean
    public  RedisTemplate<String, String> redisTemplateRelation() throws Exception{
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryRelation());
        template.afterPropertiesSet();
        return template;
    }


    @Primary
    @Bean
    public JedisConnectionFactory getConnectionFactoryRelation() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.boolRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.boolRedisProperties.getHost());
        //端口
        factory.setPort(this.boolRedisProperties.getPort());
        //默认0
        factory.setDatabase(2);
        factory.setPoolConfig(this.boolRedisProperties.startServer());

        return factory;
    }


    /**
     * 在线redis redisTemplate
     * @return
     */
    @Bean
    public  RedisTemplate<String, String> redisTemplateOnline(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryOnline());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryOnline() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.boolRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.boolRedisProperties.getHost());
        //端口
        factory.setPort(this.boolRedisProperties.getPort());
        //默认0
        factory.setDatabase(0);
        factory.setPoolConfig(this.boolRedisProperties.startServer());

        return factory;
    }

    /**
     * 流水号redis redisTemplate
     * @return
     */
    @Bean
    public  RedisTemplate<String, String> redisTemplateFlowNumber(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryFlowNumber());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryFlowNumber() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.boolRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.boolRedisProperties.getHost());
        //端口
        factory.setPort(this.boolRedisProperties.getPort());
        //默认0
        factory.setDatabase(3);
        factory.setPoolConfig(this.boolRedisProperties.startServer());

        return factory;
    }


    /**
     * 平台redis redisTemplate
     * @return
     */
    @Bean
    public  RedisTemplate<String, String> redisTemplateForwardVIN(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryForwardVIN());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryForwardVIN() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.boolRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.boolRedisProperties.getHost());
        //端口
        factory.setPort(this.boolRedisProperties.getPort());
        //默认0
        factory.setDatabase(5);
        factory.setPoolConfig(this.boolRedisProperties.startServer());

        return factory;
    }



    /**
     * 平台redis redisTemplate
     * @return
     */
    @Bean
    public  RedisTemplate<String, String> redisTemplatePlatform(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryPlatform());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryPlatform() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.boolRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.boolRedisProperties.getHost());
        //端口
        factory.setPort(this.boolRedisProperties.getPort());
        //默认0
        factory.setDatabase(6);
        factory.setPoolConfig(this.boolRedisProperties.startServer());

        return factory;
    }


    /**
     * 故障redis查询关系 redisFaultQuery  4
     * @return
     */
   @Bean
    public RedisTemplate<String, String> redisFaultQuery(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryFault());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryFault() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.boolRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.boolRedisProperties.getHost());
        //端口
        factory.setPort(this.boolRedisProperties.getPort());
        //默认4
        factory.setDatabase(4);
        factory.setPoolConfig(this.boolRedisProperties.startServer());

        return factory;
    }


    /**
     * 故障redis添加故障信息(单独，实时性比较低) redisFaultAdd  0
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisFaultAdd(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryFaultAdd());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryFaultAdd() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.faultRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.faultRedisProperties.getHost());
        //端口
        factory.setPort(this.faultRedisProperties.getPort());
        //默认0
        factory.setDatabase(0);
        factory.setPoolConfig(this.faultRedisProperties.startServer());

        return factory;
    }

    /**
     * 故障redis添加故障信息(单独，要求实时性) redisFaultRealTtime  1
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisFaultRealTtime(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactoryFaultRealTtime());
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public JedisConnectionFactory getConnectionFactoryFaultRealTtime() {

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //设置连接超时时间
        factory.setUsePool(true);
        factory.setTimeout(this.faultRedisProperties.getTimeout());
        //IP
        factory.setHostName( this.faultRedisProperties.getHost());
        //端口
        factory.setPort(this.faultRedisProperties.getPort());
        //默认1
        factory.setDatabase(1);
        factory.setPoolConfig(this.faultRedisProperties.startServer());

        return factory;
    }


    /**
     * 根据信息加载配置
     * @param boolRedisProperties
     * @param redisTemplate
     * @return
     */
    public static RedisTemplate<String, Object> initRedisTemplate(RedisProperties boolRedisProperties, RedisTemplate<String, Object> redisTemplate){
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(boolRedisProperties.getHost());
        redisConnectionFactory.setPort(boolRedisProperties.getPort());
        redisConnectionFactory.setDatabase(boolRedisProperties.getDatabase());
        redisConnectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}