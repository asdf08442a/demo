package com.demo.common.util.http;

import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * https请求工具类  
 * Created by jinzg on 2016年12月14日
 */
@SuppressWarnings("deprecation")
public class HttpsClientUtils extends DefaultHttpClient {
    private static final Logger logger                 = LoggerFactory.getLogger(HttpsClientUtils.class);

    /** httpClient 连接超时时间. */
    private static final int    CONNECTION_TIMEOUT     = 1000 * 20;                                      // 连接超时20秒

    /** httpClient数据传输超时时间. */
    private static final int    TRANS_TIMEOUT          = 1000 * 20;

    /** 数据传输格式. */
    private static final String APPLICATION_JSON       = "application/json";

    public HttpsClientUtils() throws Exception {
        super();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[] { tm }, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }

    public static String sendHttpsPostJsonData(String url, String parameters, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new HttpsClientUtils();
            // 创建httppost  
            httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(parameters, Charset.forName(charset)));
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TRANS_TIMEOUT);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (Exception e) {
                logger.error("关闭post请求流异常", e);
            }
        }
        return result;
    }

    /**
     * https post请求
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String sendHttpsPostJsonData(String url, Map<String, String> params, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new HttpsClientUtils();
            // 创建httppost
            httpPost = new HttpPost(url);
            //            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            //            httpPost.setHeader("Accept", "application/json");
            if (null != params) {
                List<NameValuePair> formParams = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formParams, (charset == null) ? "UTF-8" : charset));
            }
            //            httpPost.setEntity(new StringEntity(parameters, Charset.forName(charset)));
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TRANS_TIMEOUT);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (Exception e) {
                logger.error("关闭post请求流异常", e);
            }
        }
        return result;
    }

}