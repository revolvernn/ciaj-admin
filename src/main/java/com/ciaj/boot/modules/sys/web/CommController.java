package com.ciaj.boot.modules.sys.web;

import com.ciaj.base.AbstractBase;
import com.ciaj.base.TokenEntity;
import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.boot.component.service.ShiroService;
import com.ciaj.boot.modules.form.LoginForm;
import com.ciaj.boot.modules.sys.entity.dto.SysUserDto;
import com.ciaj.boot.modules.sys.entity.po.SysAuthPo;
import com.ciaj.boot.modules.sys.entity.po.SysMenuPo;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.service.SysAuthService;
import com.ciaj.boot.modules.sys.service.SysCommService;
import com.ciaj.boot.modules.sys.service.SysMenuService;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.constant.DefaultConfigConstant;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.pwd.PasswordEntity;
import com.ciaj.comm.utils.*;
import com.ciaj.comm.validate.ValidatorUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 14:27
 * @Description:
 */
@Api(tags = "系统-公共配置")
@Controller
public class CommController {

	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysAuthService sysAuthService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;
	@Autowired
	private SysCommService sysCommService;

	/**
	 * 处理页面按钮是否有权限显示
	 *
	 * @param codes
	 * @return
	 */
	@ApiOperation(value = "权限检查")
	@ResponseBody
	@RequiresUser
	@GetMapping("check/permissions")
	protected ResponseEntity checkPermissions(@RequestParam("codes") String codes) {

		Map<String, Boolean> map = new HashMap<>();
		map.put("IS_ALL_AUTH", CommUtil.getLoginUser().isSuperAdmin());
		map.put("DEFAULT_DATA_AUTH", ShiroUtils.checkPermissions(DefaultConfigConstant.DEFAULT_FINAL_DATA_PERMISSION_UPDATE_OR_DELETE));
		if (StringUtil.isNotBlank(codes)) {
			for (String s : codes.split(",")) {
				boolean b = ShiroUtils.checkPermissions(s);
				map.put(s.replaceAll(":", ""), b);
			}
		}
		return new ResponseEntity().put(map);
	}


	/**
	 * 验证码
	 *
	 * @param response
	 * @throws IOException
	 */
	@ApiOperation(value = "获取验证码")
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

	/**
	 * 登录方法
	 *
	 * @param loginForm
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "用户登录", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统-登录", content = "用户登录")
	@ResponseBody
	@PostMapping("sys/login")
	public ResponseEntity<TokenEntity> login(@RequestBody LoginForm loginForm) {
		ValidatorUtils.validateEntity(loginForm);
		Boolean loginFlag = false;
		for (DefaultConstant.LoginClient value : DefaultConstant.LoginClient.values()) {
			if (value.name().equalsIgnoreCase(loginForm.getLoginClient())) {
				loginFlag = true;
				break;
			}
		}
		if (!loginFlag) {
			throw new BsRException("登录方式不正确");
		}

		if (DefaultConstant.LoginClient.pc.name().equalsIgnoreCase(loginForm.getLoginClient())) {
			String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
			if (!loginForm.getCaptcha().equalsIgnoreCase(kaptcha)) {
				throw new BsRException("验证码不正确");
			}
		}
		try {
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getAccount(), loginForm.getPassword());
			token.setRememberMe(loginForm.getRememberMe());
			subject.login(token);
		} catch (UnknownAccountException e) {
			return ResponseEntity.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			throw new BsRException("账号或密码不正确");
		} catch (LockedAccountException e) {
			throw new BsRException("账号已被锁定,请联系管理员");
		} catch (AuthenticationException e) {
			throw new BsRException("账户验证失败");
		}

		return new ResponseEntity<TokenEntity>().put(new TokenEntity(ShiroUtils.getSession().getId().toString()));
	}


	/**
	 * 注册方法
	 *
	 * @param loginForm
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "用户注册", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统-注册", content = "用户注册")
	@ResponseBody
	@PostMapping("sys/register")
	public ResponseEntity register(@RequestBody LoginForm loginForm) {
		if (StringUtil.isBlank(loginForm.getAccount())) {
			throw new BsRException("手机号不能为空");
		}
		final PasswordEntity p = PasswordUtil.getPassword(loginForm.getPassword());
		SysUserPo sysUser = new SysUserPo();
		sysUser.setAccount(loginForm.getAccount());
		final List<SysUserPo> select = sysUserService.select(sysUser);
		if (CollectionUtil.isNotEmpty(select)) {
			throw new BsRException("账号已经存在");
		}

		sysUser.setUsername(sysUser.getAccount());
		sysUser.setNickname(sysUser.getAccount());
		sysUserService.insertOrUpdatePre(sysUser, AbstractBase.INSERT);
		sysUserService.insert(sysUser);

		SysAuthPo sysAuth = new SysAuthPo();
		sysAuth.setPassword(p.getPassword());
		sysAuth.setSalt(p.getSalt());
		sysAuthService.insertOrUpdatePre(sysAuth, AbstractBase.INSERT);
		sysAuthService.insert(sysAuth);
		return ResponseEntity.success("注册成功");
	}


	/**
	 * 获取导航菜单
	 *
	 * @return
	 */
	@ApiOperation(value = "获取导航菜单")
	@OperationLog(operation = "系统-导航", content = "获取导航菜单")
	@RequiresUser
	@ResponseBody
	@GetMapping("sys/menu/nav")
	public ResponseEntity<SysMenuPo> nav() {
		List<SysMenuPo> list = sysMenuService.selectNav();
		return ResponseEntity.success().put(list);
	}


	/**
	 * PC注册方法
	 *
	 * @param entity
	 * @return
	 */
	@Resubmit
	@ApiOperation(value = "PC端注册", produces = "application/json;charset=UTF-8")
	@OperationLog(operation = "系统-注册", content = "PC端注册")
	@ResponseBody
	@PostMapping("sys/pc/register")
	public ResponseEntity pcRegister(@RequestBody SysUserDto entity) {
		if (StringUtil.isBlank(entity.getMobile())) {
			throw new BsRException("手机号不能为空");
		}

		SysUserPo q = new SysUserPo();
		q.setUsername(entity.getUsername());
		q.setDelFlag(DefaultConstant.FLAG_N);
		final List<SysUserPo> select = sysUserService.select(q);
		if (CollectionUtil.isNotEmpty(select)) {
			throw new BsRException("用户名已经存在");
		}
		SysUserPo user = new SysUserPo();
		BeanUtils.copyProperties(entity, user);
		String password = user.getMobile().substring(entity.getMobile().length() - 6);
		final PasswordEntity p = PasswordUtil.getPassword(password);
		sysUserService.insertOrUpdatePre(user, AbstractBase.INSERT);
		sysUserService.insert(user);

		SysAuthPo sysAuth = new SysAuthPo();
		sysAuth.setUserId(user.getId());
		sysAuth.setPassword(p.getPassword());
		sysAuth.setSalt(p.getSalt());
		sysAuthService.insertOrUpdatePre(sysAuth, AbstractBase.INSERT);
		sysAuthService.insert(sysAuth);

		return ResponseEntity.success("添加成功").put(entity);
	}

	/**
	 * 获取当前登录用户
	 *
	 * @return
	 */
	@ApiOperation(value = "获取当前登录用户")
	@OperationLog(operation = "系统-用户", content = "获取当前登录用户")
	@RequiresUser
	@ResponseBody
	@GetMapping("sys/user/info")
	public ResponseEntity userInfo() {
		final ShiroUser loginUser = CommUtil.getLoginUser();
		return ResponseEntity.success().put(loginUser);
	}


	/**
	 * 更新当前登录用户角色
	 *
	 * @return
	 */
	@ApiOperation(value = "更新当前登录用户角色")
	@OperationLog(operation = "系统-用户", content = "更新当前登录用户角色")
	@RequiresUser
	@ResponseBody
	@PostMapping("sys/user/role/change")
	public ResponseEntity roleChange(@RequestParam(value = "roleId", required = true) String roleId) {
		ShiroUser shiroUser = shiroService.updateShiroUser(roleId);
		return ResponseEntity.success().put(shiroUser);
	}

	/**
	 * 密码修改
	 *
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@ApiOperation(value = "密码修改")
	@Resubmit
	@OperationLog(operation = "系统-用户", content = "密码修改")
	@RequiresUser
	@ResponseBody
	@PostMapping("sys/user/password")
	public ResponseEntity updatePassword(String password, String newPassword) {
		if (StringUtil.isBlank(password) || StringUtil.isBlank(newPassword)) {
			return ResponseEntity.error("密码不能为空");
		}
		final ShiroUser loginUser = CommUtil.getLoginUser();
		SysAuthPo authQuery = new SysAuthPo();
		authQuery.setDelFlag(DefaultConstant.FLAG_N);
		authQuery.setUserId(loginUser.getId());
		List<SysAuthPo> auths = sysAuthService.select(authQuery);

		final SysAuthPo sysAuth = auths.get(0);
		final PasswordEntity password1 = PasswordUtil.getPassword(password, sysAuth.getSalt());


		if (!sysAuth.getPassword().equals(password1.getPassword())) {
			return ResponseEntity.error("原密码不正确");
		}
		SysAuthPo auth = new SysAuthPo();
		final PasswordEntity password2 = PasswordUtil.getPassword(newPassword);
		auth.setId(sysAuth.getId());
		auth.setPassword(password2.getPassword());
		auth.setSalt(password2.getSalt());
		sysAuthService.insertOrUpdatePre(sysAuth, AbstractBase.UPDATE);
		sysAuthService.updateByPrimaryKeySelective(auth);
		return ResponseEntity.success();
	}


	/**
	 * 用户密码修改
	 *
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	@ApiOperation(value = "用户密码修改")
	@Resubmit
	@OperationLog(operation = "系统-用户", content = "用户密码修改")
	@ResponseBody
	@RequiresPermissions("sys:user:password:update")
	@PostMapping("sys/user/password/update")
	public ResponseEntity updateUserPassword(String userId, String newPassword) {
		if (StringUtil.isBlank(newPassword)) {
			return ResponseEntity.error("密码不能为空");
		}
		SysAuthPo authQuery = new SysAuthPo();
		authQuery.setDelFlag(DefaultConstant.FLAG_N);
		authQuery.setUserId(userId);
		List<SysAuthPo> auths = sysAuthService.select(authQuery);

		final SysAuthPo sysAuth = auths.get(0);

		SysAuthPo auth = new SysAuthPo();

		final PasswordEntity password = PasswordUtil.getPassword(newPassword);
		auth.setId(sysAuth.getId());
		auth.setPassword(password.getPassword());
		auth.setSalt(password.getSalt());
		sysAuthService.insertOrUpdatePre(auth, AbstractBase.UPDATE);
		sysAuthService.updateByPrimaryKeySelectiveAndVersion(auth, sysAuth.getVersion());
		return ResponseEntity.success();
	}


	/**
	 * 导出用户
	 *
	 * @return
	 */
	@ApiOperation(value = "导出用户")
	@RequiresUser
	@GetMapping("sys/export/users")
	public void export(HttpServletResponse response, HttpServletRequest request) {
		List<SysUserPo> sysUserPos = sysUserService.selectAll(null);
		new ExcelUtil().build("用户", new String[]{"id", "account", "username"}, new String[]{"id", "账号", "用户名"}, sysUserPos).exportExcel(request, response);
	}


	/**
	 * 系统表结构统计
	 *
	 * @return
	 */
	@ApiOperation(value = "系统表结构统计")
	@ResponseBody
	@RequiresUser
	@GetMapping("sys/table/status")
	public ResponseEntity<List<Map<String, Object>>> tableStatus() {
		List<Map<String, Object>> tables = sysCommService.selectTableStatus();
		return new ResponseEntity<List<Map<String, Object>>>().put(tables);
	}


	/**
	 * 登出方法
	 *
	 * @return
	 */
	@ApiOperation(value = "退出登录")
	@OperationLog(operation = "系统-登出", content = "退出登录")
	@GetMapping("sys/logout")
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
}
