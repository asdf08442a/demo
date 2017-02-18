package com.jiedaibao.demo.common.enums;

public enum FTPOptType {
    UP("上传"), 
    DOWN("下载"), 
    LIST("浏览"), 
    DELFILE("删除文件"), 
    DELFOD("删除文件夹"), 
    RENAME("上传"); 

    private String optname;

    FTPOptType(String optname) {
        this.optname = optname;
    }

    public String getOptname() {
        return optname; 
    }
}
