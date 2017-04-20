package com.demo.api.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 请求dto
 *
 * @author jinzg
 * @create 2017-03-22 13:54
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 3688898483812002530L;
    @NotEmpty
    private String s1;
    private String s2;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    @Override
    public String toString() {
        return "Request{" +
                "s1='" + s1 + '\'' +
                ", s2='" + s2 + '\'' +
                '}';
    }
}
