package com.ciaj.boot.component.config.shiro;


import com.ciaj.comm.utils.PasswordUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public DefaultWebSecurityManager securityManager(AdminShiroRealm adminShiroRealm, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(adminShiroRealm);
		securityManager.setSessionManager(sessionManager);
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
	public SessionManager sessionManager(SimpleCookie simpleCookie) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookie(simpleCookie);
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}

	@Bean
	public SessionDAO sessionDAO() {
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		return enterpriseCacheSessionDAO;
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
		filterMap.put("/**", "authc");
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