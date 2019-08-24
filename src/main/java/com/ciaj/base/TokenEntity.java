package com.ciaj.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/11 10:00
 * @Description:
 */
@Data
@AllArgsConstructor
public class TokenEntity  implements Serializable {

    private static final long serialVersionUID = -4402402881046290312L;
    private String token;
}
