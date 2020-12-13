package com.github.linfeng.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Http请求工具类
 *
 * @author 黄麟峰
 * @date 2020/11/29 22:50
 */
public class HttpClientUtils {

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
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
            sslcontext,
            new String[]{"TLSv1"},
            null,
            SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        try (CloseableHttpClient httpclient = HttpClients.custom()
            .setSSLSocketFactory(sslSocketFactory)
            .build()) {

            HttpGet httpget = new HttpGet(url);

            System.out.println("执行请求：" + httpget.getRequestLine());


            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                ret = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);

                System.out.println("执行返回：" + ret);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
