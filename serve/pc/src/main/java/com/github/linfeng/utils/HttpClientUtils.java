package com.github.linfeng.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Http请求工具类
 *
 * @author 黄麟峰
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    private HttpClientUtils() {
    }

    /**
     * get请求
     *
     * @param url 请求地址
     * @return 请求结果
     */
    public static String getRequest(String url) {
        // TODO: get请求的实现
        String ret = null;

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom()
                //.loadTrustMaterial(new File("my.keystore"), "nopassword".toCharArray(),
                //    new TrustSelfSignedStrategy())
                .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslcontext)
            .setTlsVersions(TLS.V_1_0).build();
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslSocketFactory).build();
        try (CloseableHttpClient httpclient = HttpClients.custom()
            .setConnectionManager(cm)
            .build()) {

            HttpGet httpget = new HttpGet(url);

            LOGGER.info("执行{}请求：{}", httpget.getMethod(), httpget.getRequestUri());
            ret = httpclient.execute(httpget, response -> {
                HttpEntity entity = response.getEntity();
                if (response.getCode() >= HttpStatus.SC_REDIRECTION) {
                    EntityUtils.consume(entity);
                    LOGGER.info("请求出现异常,状态码:{}", response.getCode());
                    return null;
                } else if (response.getEntity() != null) {
                    String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("请求结果:{}", body);
                    }
                    return body;
                } else {
                    LOGGER.info("请求出现异常,状态码:{}", response.getCode());
                    return null;
                }
            });

            LOGGER.info("执行返回：{}", ret);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * post请求
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 请求结果
     */
    public static String postRequest(String url, Map<String, String> data) {
        // TODO: post请求的实现
        return "";
    }
}
