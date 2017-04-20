package com.demo.common.util.sftp;

/**
 * Created by wupingping on 2016/08/02.
 */
public class SFTPDetails {

    /**
     * 请求地址
     */
    private String reqHost;

    /**
     * 请求端口号
     */
    private int    reqPort;

    /**
     * 账号
     */
    private String reqUserName;

    /**
     * 密码
     */
    private String reqPassword;

    /**
     * 默认端口号
     */
    private int    defaultPort;

    /**
     *超时时间，单位毫秒
     */
    private int    timeOut;

    /**
     * 主机访问方式 0 IP加端口访问 1 域名访问
     */
    private int    reqType;

    /**
     * ftp文件目录
     */
    private String ftpDir;

    /**
     * 本地文件目录
     */
    private String localDir;

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getReqHost() {
        return reqHost;
    }

    public void setReqHost(String reqHost) {
        this.reqHost = reqHost;
    }

    public String getReqPassword() {
        return reqPassword;
    }

    public void setReqPassword(String reqPassword) {
        this.reqPassword = reqPassword;
    }

    public String getReqUserName() {
        return reqUserName;
    }

    public void setReqUserName(String reqUserName) {
        this.reqUserName = reqUserName;
    }

    public int getReqPort() {
        return reqPort;
    }

    public void setReqPort(int reqPort) {
        this.reqPort = reqPort;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public String getFtpDir() {
        return ftpDir;
    }

    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    @Override
    public String toString() {
        return "SFTPDetails [reqHost=" + reqHost + ", reqPort=" + reqPort + ", reqUserName="
               + reqUserName + ", reqPassword=" + reqPassword + ", defaultPort=" + defaultPort
               + ", timeOut=" + timeOut + ", reqType=" + reqType + ", ftpDir=" + ftpDir
               + ", localDir=" + localDir + "]";
    }

}
