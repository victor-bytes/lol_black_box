package com.qq.lol.utils;

import com.qq.lol.dto.LolClientDto;
import lombok.Data;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023/11/29 - 11 - 29 - 12:43
 * @Description: 处理网络请求
 * @version: 1.0
 */
@Data
public class NetRequestUtil {

    private static String defaultHost;
    private OkHttpClient client = myHttpClient();
    private static Headers defaultHeaders;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static NetRequestUtil netRequestUtil;

    private static LolClientDto riotClientDto;

    private NetRequestUtil(){}

    // 初始化请求头
    private NetRequestUtil(LolClientDto bo) {
        defaultHost = "https://127.0.0.1:" + bo.getPort();
        String basic = Credentials.basic("riot", bo.getToken());
        defaultHeaders = new Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", basic)
                .build();
    }

    public static Headers getDefaultHeaders() {
        return defaultHeaders;
    }

    public static String getDefaultHost() {
        return defaultHost;
    }

    public OkHttpClient getClient() {
        return client;
    }

    // 懒汉式单例模式
    public static NetRequestUtil getNetRequestUtil(){
        try {
            if(netRequestUtil == null) {
                System.out.println(StandardOutTime.getCurrentTime()+ " 第一次初始化NetRequestUtil---");

                // 获取 Port和 Token
                riotClientDto = ProcessUtil.getClientProcess();
                // 实例化 NetRequestUtil，用于返回
                if(riotClientDto.equals(new LolClientDto())) {
                    /*
                    此处当客户端未启动时，会返回 无参构造的NetRequestUtil，以供 service初始化静态变量
                    MainAPP中会检测到客户端未启动，并给出弹窗提示。
                     */
                    return new NetRequestUtil();
                }
                netRequestUtil = new NetRequestUtil(riotClientDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return netRequestUtil;
    }

    /**
     *
     * @param host 指定主机
     * @param endpoint api
     * @return
     * @throws IOException
     */
    public String doGet(String host, String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(host + endpoint)
                .get()
                .headers(defaultHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public Response doGetV2(String endpoint) {
        Request request = new Request.Builder()
                .url(defaultHost + endpoint)
                .get()
                .headers(defaultHeaders)
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 使用默认主机
     * @param endpoint
     * @return
     * @throws IOException
     */
    public String doGet(String endpoint){
        try {
            return this.doGet(defaultHost, endpoint);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "doGet方法异常";
    }

    public String doPost(String endpoint, String bodyStr) throws IOException {
        return this.doPost(defaultHost, endpoint, bodyStr);
    }

    public String doPut(String endpoint, String bodyStr) throws IOException {
        return this.doPut(defaultHost, endpoint, bodyStr);
    }

    public String doPut(String host, String endpoint, String bodyStr) throws IOException {
        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(host + endpoint)
                .put(body)
                .headers(defaultHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPost(String host, String endpoint, String bodyStr) throws IOException {
        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(host + endpoint)
                .post(body)
                .headers(defaultHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private OkHttpClient myHttpClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(getSslSocketFactory(), getX509TrustManager())
                .hostnameVerifier(getHostnameVerifier())
                .retryOnConnectionFailure(Boolean.TRUE)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2))
                .build();
    }

    private static SSLSocketFactory getSslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    public static HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }

    public static X509TrustManager getX509TrustManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trustManager;
    }

}

