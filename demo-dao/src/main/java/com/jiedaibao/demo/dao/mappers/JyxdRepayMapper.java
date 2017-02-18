package com.jiedaibao.demo.dao.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jiedaibao.demo.dao.bean.JyxdRepay;

public interface JyxdRepayMapper extends BaseMapper<JyxdRepay> {
    List<JyxdRepay> getYesJyxdRepays(@Param("bizDate")String bizDate);
    JyxdRepay getByRepayInfoId(@Param("repayInfoId")String repayInfoId);
}
