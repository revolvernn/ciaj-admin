package com.ciaj.base;

import java.io.Serializable;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/11 10:00
 * @Description:
 */
public class TokenEntity  implements Serializable {

    private static final long serialVersionUID = -4402402881046290312L;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenEntity(String token) {
        this.token = token;
    }
}
