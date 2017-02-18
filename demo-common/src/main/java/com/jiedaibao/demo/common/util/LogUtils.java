package com.jiedaibao.demo.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * 日志工具类
 * Created by jinzg on 2016年12月12日
 */
public class LogUtils {
    /**
     * 记录debug日志 
     */
    public static void debug(Logger logger, String msg, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format(msg, params));
        }
    }

    /**
     * 记录debug日志 
     */
    public static void debug(Logger logger, String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    /**
     * 记录info日志 
     */
    public static void info(Logger logger, String msg, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format(msg, params));
        }
    }

    /**
     * 记录info日志 
     */
    public static void info(Logger logger, String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    /**
     * 记录warn日志 
     */
    public static void warn(Logger logger, String msg, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(String.format(msg, params));
        }
    }

    /**
     * 记录warn日志 
     */
    public static void warn(Logger logger, Throwable e, String msg, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(String.format(msg, params), e);
        }
    }

    /**
     * 记录error日志 
     */
    public static void error(Logger logger, Throwable e, String msg, Object... params) {
        logger.error(String.format(msg, params), e);
    }

    /**
     * 记录error日志 
     */
    public static void error(Logger logger, String msg, Object... params) {
        logger.error(String.format(msg, params));
    }

    /**
     * 异常堆栈转String
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        Writer sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
    
    /**
	 * 日志中流水号，方便查询日志
	 * @param orgJrnNo 传入的流水号
	 */
	public static void getLogger(String orgJrnNo){
		MDC.put("orgJrnNo", orgJrnNo);
	}
}
