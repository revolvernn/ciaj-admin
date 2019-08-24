package com.ciaj.boot.modules.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/10 15:36
 * @Description:
 */
@Data
public class LoginForm implements Serializable {
    private static final long serialVersionUID = -2516079332232636288L;
    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String captcha;

    @NotBlank(message = "登录方式不正确")
    private String loginClient;

    private Boolean rememberMe = Boolean.FALSE;
}
