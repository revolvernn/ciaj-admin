package com.ciaj.boot.component.config.shiro;

import com.ciaj.boot.component.service.ShiroService;
import com.ciaj.boot.modules.sys.entity.po.SysAuthPo;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.service.SysAuthService;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.CommUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 10:31
 * @Description:
 */
@Log4j2
public class AdminShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ShiroService shiroService;

    @Resource
    private SysAuthService sysAuthService;

    /**
     * 授权权限验证
     *
     * @param principals
     *
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final ShiroUser loginUser = CommUtil.getLoginUser();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(loginUser.getStringRoles());
        authorizationInfo.setStringPermissions(loginUser.getStringPermissions());
        return authorizationInfo;
    }

    /**
     *
     * @param token
     * @return
     * @throws
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("MyShiroRealm.doGetAuthenticationInfo()");
        String principal = (String) token.getPrincipal();
        SysUserPo t = new SysUserPo();
        t.setAccount(principal);
        t.setDelFlag(DefaultConstant.FLAG_N);

        SysUserPo sysUser = sysUserService.selectOne(t);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法

        if (sysUser == null) {
            throw new UnknownAccountException("账号不存在");
        }
        if (DefaultConstant.FLAG_Y.equals(sysUser.getLocked())) { //账户冻结
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SysAuthPo authQuery = new SysAuthPo();
        authQuery.setDelFlag(DefaultConstant.FLAG_N);
        authQuery.setUserId(sysUser.getId());
        List<SysAuthPo> auths = sysAuthService.select(authQuery);

        if (CollectionUtils.isEmpty(auths)) throw new BsRException("账号或密码不正确");

        SysAuthPo sysAuth = auths.get(0);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                principal, //用户账号
                sysAuth.getPassword(), //密码
                ByteSource.Util.bytes(sysAuth.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        ShiroUser loginUser = shiroService.selectShiroUser(sysUser.getId());
        SecurityUtils.getSubject().getSession().setAttribute(DefaultConstant.LOGIN_USER, loginUser);
        return authenticationInfo;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        log.info("hasRole{},{}", principals, permission);
        final ShiroUser loginUser = CommUtil.getLoginUser();
        return loginUser.isSuperAdmin() || super.isPermitted(principals, permission);
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        log.info("hasRole{},{}", principals, roleIdentifier);
        final ShiroUser loginUser = CommUtil.getLoginUser();
        return loginUser.isSuperAdmin() || super.hasRole(principals, roleIdentifier);
    }
}