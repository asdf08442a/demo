package com.demo.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
@TableName("t_user")
public class User {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @Min(18)
    private int age;
    @TableField("biz_date")
    private String bizDate;
    @TableField("create_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    @TableField("update_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;
}