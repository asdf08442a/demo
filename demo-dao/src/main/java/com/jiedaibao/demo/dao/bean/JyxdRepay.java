package com.jiedaibao.demo.dao.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款信息
 * Created by jinzg on 2016年11月23日.
 */
public class JyxdRepay implements Serializable {

    private static final long serialVersionUID = 3417236852619687940L;
    private String            id;                                     //ID
    private String            repayPlatformId;                        //配资流水号ID
    private String            repaySysId;                             //还款系统ID
    private String            productId;                              //借贷宝标的ID
    private String            contractId;                             //小贷生成的合同单号
    private String            custNo;                                 //小贷系统生成的客户号
    private String            userName;                               //客户姓名
    private String            idType;                                 //证件类型
    private String            idNo;                                   //证件号码
    private String            repayTime;                              //还款时间格式:yyyy-MM-dd HH:mm
    private BigDecimal        repayAmt;                               //还款金额
    private BigDecimal        capitalAmt;                             //本金
    private BigDecimal        interestAmt;                            //利息
    private BigDecimal        amerceAmt;                              //罚息
    private String            status;                                 //是否结清:1:结清 2:未结清
    private String            code;                                   //响应码
    private String            msg;                                    //还款结果描述
    private String            bizDate;                                //业务日期yyyyMMdd
    private Date              createTime;                             //创建时间
    private Date              updateTime;                             //更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepayPlatformId() {
        return repayPlatformId;
    }

    public void setRepayPlatformId(String repayPlatformId) {
        this.repayPlatformId = repayPlatformId;
    }

    public String getRepaySysId() {
        return repaySysId;
    }

    public void setRepaySysId(String repaySysId) {
        this.repaySysId = repaySysId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public BigDecimal getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(BigDecimal repayAmt) {
        this.repayAmt = repayAmt;
    }

    public BigDecimal getCapitalAmt() {
        return capitalAmt;
    }

    public void setCapitalAmt(BigDecimal capitalAmt) {
        this.capitalAmt = capitalAmt;
    }

    public BigDecimal getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(BigDecimal interestAmt) {
        this.interestAmt = interestAmt;
    }

    public BigDecimal getAmerceAmt() {
        return amerceAmt;
    }

    public void setAmerceAmt(BigDecimal amerceAmt) {
        this.amerceAmt = amerceAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "JyxdRepay [id=" + id + ", repayPlatformId=" + repayPlatformId + ", repaySysId="
               + repaySysId + ", productId=" + productId + ", contractId=" + contractId
               + ", custNo=" + custNo + ", userName=" + userName + ", idType=" + idType + ", idNo="
               + idNo + ", repayTime=" + repayTime + ", repayAmt=" + repayAmt + ", capitalAmt="
               + capitalAmt + ", interestAmt=" + interestAmt + ", amerceAmt=" + amerceAmt
               + ", status=" + status + ", code=" + code + ", msg=" + msg + ", bizDate=" + bizDate
               + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }

}
