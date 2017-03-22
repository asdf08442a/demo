package com.jiedaibao.demo.common.util.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * http请求工具类
 * Created by jinzg on 2016年12月14日
 */
public class HttpClientUtils {
    private static final Logger log    = LoggerFactory.getLogger(HttpClientUtils.class);
    private static HttpClient   client = HttpClients.createDefault();

    /**
     * http get请求
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static String sendGetRequest(String url) throws Exception {
        return sendGetRequest(url, null);
    }

    /**
     * http get请求
     *
     * @param url           请求地址
     * @param decodeCharset 字符集编码  默认UTF-8
     * @return
     * @throws Exception
     */
    public static String sendGetRequest(String url, String decodeCharset) throws Exception {
        long start = System.currentTimeMillis();
        String responseContent = null;
        HttpGet httpGet = new HttpGet(url);
        HttpEntity entity = null;

        try {
            //发送请求
            HttpResponse response = client.execute(httpGet);
            //获取返回实体
            entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, (decodeCharset == null) ? "UTF-8" : decodeCharset);
            }
            log.info("调用接口{},result:{},耗时为{}毫秒", new Object[] { url, responseContent, System.currentTimeMillis() - start });
        } catch (Exception e) {
            log.error("访问" + url + "异常,信息如下", e);
            throw e;
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (Exception ex) {
                log.error("net io handler ", ex);
            }
        }
        if (responseContent == null) {
            log.info("url[{}] failed", url);
        } else {
            log.info("url[{}] ret[{}]", url, responseContent);
        }
        return responseContent;
    }

    /**
     * 发送http get请求
     *
     * @param url    请求地址
     * @param params 请求的参数
     * @return
     * @throws Exception
     */
    public static String sendGetRequestParam(String url, Map<String, String> params) throws Exception {
        StringBuffer sbufUrl = new StringBuffer(url);
        //参数拼装
        if (url.indexOf("?") != -1) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sbufUrl.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        } else {
            sbufUrl.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sbufUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sbufUrl.delete(sbufUrl.lastIndexOf("&"), sbufUrl.length());
        }
        //发送http get请求
        return sendGetRequest(sbufUrl.toString(), null);
    }

    /**
     * http post 请求
     *
     * @param url    请求地址
     * @param params 请求参数  map<String,String>类型
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(String url, Map<String, String> params) throws Exception {
        return sendPostRequest(url, params, null);
    }

    /**
     * http post 请求
     *
     * @param url           请求地址
     * @param params        请求参数   map<String,String>类型
     * @param decodeCharset 字符集编码   默认UTF-8
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(String url, Map<String, String> params, String decodeCharset) throws Exception {
        long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        HttpEntity httpEntity = null;
        String responseContent = null;

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, (decodeCharset == null) ? "UTF-8" : decodeCharset);
            post.setEntity(entity);
            //发送http post请求
            HttpResponse response = client.execute(post);
            //获取返回实体
            httpEntity = response.getEntity();
            //如果返回实体不为空
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, (decodeCharset == null) ? "UTF-8" : decodeCharset);
            }
            log.info("调用接口{} 参数{} 返回结果{} 耗时为{}毫秒",
                new Object[] { url, JSONObject.toJSONString(params), responseContent, System.currentTimeMillis() - start });
        } catch (Exception ex) {
            log.error("访问" + url + "异常,信息如下", ex);
            throw ex;
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (Exception ex) {
                log.error("net io handler ", ex);
            }
        }
        return responseContent;
    }

    /**
     * http post 请求
     *
     * @param url            请求地址
     * @param requestContent 请求参数  String 类型
     * @param decodeCharset  字符集编码 默认UTF-8
     * @return
     */
    public static String sendPostRequestHttp(String url, String requestContent, String decodeCharset) {
        long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        HttpEntity httpEntity = null;
        String responseContent = null;
        try {
            post.setEntity(new StringEntity(requestContent, "UTF-8"));
            //发送http post请求
            HttpResponse response = client.execute(post);
            httpEntity = response.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, (decodeCharset == null) ? "UTF-8" : decodeCharset);
            }
            log.info("调用接口{},param{},result{}耗时为{}毫秒", new Object[] { url, requestContent, responseContent, System.currentTimeMillis() - start });
        } catch (Exception ex) {
            log.error("访问" + url + "异常,信息如下", ex);
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (Exception ex) {
                log.error("net io handler ", ex);
            }
        }
        return responseContent;
    }

}
