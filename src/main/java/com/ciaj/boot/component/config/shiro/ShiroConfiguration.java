package com.ciaj.boot.component.config.shiro;


import com.ciaj.boot.component.config.redis.StringRedisSerializer;
import com.ciaj.comm.utils.PasswordUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 10:29
 * @Description:
 */
@Log4j2
@Configuration
public class ShiroConfiguration {

	/**
	 * 自定义的Realm
	 */
	@Bean(name = "adminShiroRealm")
	public AdminShiroRealm adminShiroRealm() {
		AdminShiroRealm adminShiroRealm = new AdminShiroRealm();
		adminShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return adminShiroRealm;
	}

	@Bean
	public DefaultWebSecurityManager securityManager(AdminShiroRealm adminShiroRealm, SessionManager sessionManager,RedisConnectionFactory factory) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(adminShiroRealm);
		securityManager.setCacheManager(shiroRedisCacheManager(factory));
		securityManager.setSessionManager(sessionManager);
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie cookie = new SimpleCookie("ciaj-session-id");
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		cookie.setHttpOnly(false);
		return cookie;
	}

	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		// 设置cookie的过期时间，单位为秒，这里为一天
		cookie.setMaxAge(86400);
		return cookie;
	}

	/**
	 * cookie管理对象
	 * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	@Bean
	public SessionManager sessionManager(SimpleCookie simpleCookie,RedisConnectionFactory factory) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookie(simpleCookie);
		sessionManager.setSessionDAO(sessionDAO(factory));
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}
	@Bean("shiroRedisTemplate")
	public RedisTemplate<byte[], Object> shiroRedisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<byte[], Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		return template;
	}
	@Bean
	@DependsOn("shiroRedisTemplate")
	public SessionDAO sessionDAO(RedisConnectionFactory factory) {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO(shiroRedisTemplate(factory));
		return redisSessionDAO;
	}

	@Bean(name="shrioRedisCacheManager")
	@DependsOn("shiroRedisTemplate")
	public ShiroRedisCacheManager shiroRedisCacheManager(RedisConnectionFactory factory) {
		ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager(shiroRedisTemplate(factory));
		return cacheManager;
	}


	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/login.html");
		shiroFilter.setUnauthorizedUrl("/");

		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/swagger/**", "anon");
		filterMap.put("/v2/api-docs", "anon");
		filterMap.put("/swagger-ui.html", "anon");
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/swagger-resources/**", "anon");

		filterMap.put("/statics/**", "anon");
		filterMap.put("/captcha.jpg", "anon");
		filterMap.put("/login.html", "anon");
		filterMap.put("/sys/login", "anon");
		filterMap.put("/favicon.ico", "anon");

		filterMap.put("/oss/file/**", "anon");
		// filterMap.put("/**", "authc");
		// 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
		// 进行身份认证后才能访问
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		// user指的是用户认证通过或者配置了Remember Me记住用户登录状态后可访问
		filterMap.put("/**", "user");
		shiroFilter.setFilterChainDefinitionMap(filterMap);

		return shiroFilter;
	}

	/**
	 * 凭证匹配器
	 *
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.ALGORITHM_NAME_MD5);
		hashedCredentialsMatcher.setHashIterations(PasswordUtil.HASH_ITERATIONS);
		return hashedCredentialsMatcher;
	}

	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}


	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}
}