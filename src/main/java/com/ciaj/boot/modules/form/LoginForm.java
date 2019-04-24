package com.ciaj.boot.modules.form;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/10 15:36
 * @Description:
 */
public class LoginForm implements Serializable {
    private static final long serialVersionUID = -2516079332232636288L;
    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    private Boolean rememberMe = Boolean.FALSE;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
