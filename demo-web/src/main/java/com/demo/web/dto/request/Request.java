package com.demo.web.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 请求dto
 *
 * @author jinzg
 * @create 2017-03-22 13:54
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = 3688898483812002530L;
    @NotEmpty
    private String s1;
    private String s2;

}
