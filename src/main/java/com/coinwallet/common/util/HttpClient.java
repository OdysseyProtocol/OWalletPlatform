package com.coinwallet.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpClient {

    public String METHOD_NAME = "GET";

    private String URL = "";

    private HttpHost httpHost = null;

    private CredentialsProvider credsProvider = null;

    private HttpClientContext httpClientContext = null;

    private CloseableHttpClient httpclient = null;

    private CloseableHttpResponse closeableHttpResponse = null;

    private HttpGet httpGet = null;

    private HttpPost httpPost = null;

    private int socketTimeout = 20000;

    private int connectTimeout = 20000;

    private int connectionRequestTimeout = 20000;

    private static int maxConnTotal = 10;

    private static int maxConnPerRoute = 20;
    private String accept = "application/json";
    private String referer = "";
    private String contentType = "application/json";
    private String origin = "";
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";
    private boolean autoSaveReferer;
 

    public void setAutoSaveReferer(boolean autoSaveReferer) {
        this.autoSaveReferer = autoSaveReferer;
    }
 

    public void setReferer(String referer) {
        this.referer = referer;
    }
 

    public void setAccept(String accept) {
        this.accept = accept;
    }
 

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
 

    public void setOrigin(String origin) {
        this.origin = origin;
    }
 

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
 

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
 
 

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
 

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
 

    public static void setMaxConnTotal(int maxConnTotal) {
        HttpClient.maxConnTotal = maxConnTotal;
    }
 

    public static void setMaxConnPerRoute(int maxConnPerRoute) {
        HttpClient.maxConnPerRoute = maxConnPerRoute;
    }
 
    private boolean isAutoSaveReferer() {
        return autoSaveReferer;
    }
 

    public HttpClient() {
        initHttpRequest();
    }
 

    public HttpClient(String URL) {
        this.URL = URL;
        initHttpRequest();
    }

    public HttpClient(String URL, String METHOD_NAME) {
        this.URL = URL;
        this.METHOD_NAME = METHOD_NAME;
        initHttpRequest();
    }
 

    public void initHttpRequest() {
        if (METHOD_NAME.equalsIgnoreCase("get")) {
            if (httpGet == null) {
                if (StringUtil.isNotEmpty(URL)) {
                    httpGet = new HttpGet(URL);
                } else {
                    httpGet = new HttpGet();
                }
            } else {
                try {
                    httpGet.setURI(new java.net.URI(URL));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            httpGet.setHeader("Accept", accept);
            httpGet.setHeader("User-Agent", userAgent);
            if (StringUtil.isNotEmpty(contentType)) {
                httpGet.setHeader("Content-Type", contentType);
            }
            if (StringUtil.isNotEmpty(referer)) {
                httpGet.setHeader("Referer", referer);
            }
        } else if (METHOD_NAME.equalsIgnoreCase("post")) {
            if (httpPost == null) {
                if (StringUtil.isNotEmpty(URL)) {
                    httpPost = new HttpPost(URL);
                } else {
                    httpPost = new HttpPost();
                }
            } else {
                try {
                    httpPost.setURI(new java.net.URI(URL));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            httpPost.setHeader("Accept", accept);
            if (StringUtil.isNotEmpty(contentType)) {
                httpPost.setHeader("Content-Type", contentType);
            }
            httpPost.setHeader("Origin", origin);
            httpPost.setHeader("User-Agent", userAgent);
            if (StringUtil.isNotEmpty(referer)) {
                httpPost.setHeader("Referer", referer);
            }
        }
        initHttpClinet();
    }
    public void setMethod(String method) throws Exception {
        try {
            if (StringUtil.isNotEmpty(method)) {
                METHOD_NAME = method;
                initHttpRequest();
            } else {
                throw new Exception("参数不能为空,method is null");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
 

    public void closeClient(boolean isInit) {
        if (httpclient != null) {
            try {
                // 关闭请求
                httpclient.getConnectionManager().shutdown();
                HttpClientUtils.closeQuietly(httpclient);
                httpclient = null;
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
                if (httpGet != null) {
                    httpGet = null;
                }
                if (httpPost != null) {
                    httpPost = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isInit) {
                    initHttpClinet();
                }
            }
        }
    }
 

    public void initHttpClinet() {
        if (httpclient == null) {
            try {
                SSLContext sslcontext = createIgnoreVerifySSL();
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", new SSLConnectionSocketFactory(sslcontext))
                        .build();
                Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
                Protocol.registerProtocol("https", myhttps);
                PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                RequestConfig defaultRequestConfig = RequestConfig.custom()
                        .setSocketTimeout(socketTimeout)
                        .setConnectTimeout(connectTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout)
                        .setStaleConnectionCheckEnabled(true)
                        .build();
 
                httpclient = HttpClients.custom()
                        .setDefaultRequestConfig(defaultRequestConfig).setMaxConnTotal(maxConnTotal)
                        .setMaxConnPerRoute(maxConnPerRoute).setConnectionManager(connManager)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    public String request() throws Exception {
        try {
            HttpEntity entity = request(URL);
            InputStream inputStream = entity.getContent();
            if (inputStream != null) {
                return readLineInputStream("utf-8", inputStream);
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new Exception(msgException(e));
        } finally {
            closeHttpResponse();
        }
    }
 
    public HttpEntity request(String url) throws Exception {
        try {
            if (StringUtil.isNotEmpty(url)) {
                URL = url;
            } else {
                throw new Exception("url is null");
            }
            if (StringUtil.isEmpty(origin)) {
                origin = URL;
            }
            initHttpRequest();
            if (httpHost != null) {
                closeableHttpResponse = httpclient.execute(httpHost, METHOD_NAME.equalsIgnoreCase("get") ? httpGet : httpPost);
            } else if (httpHost != null && httpClientContext != null) {
                closeableHttpResponse = httpclient.execute(httpHost, METHOD_NAME.equalsIgnoreCase("get") ? httpGet : httpPost, httpClientContext);
            } else {
                closeableHttpResponse = httpclient.execute(METHOD_NAME.equalsIgnoreCase("get") ? httpGet : httpPost);
            }
            if (isAutoSaveReferer()) {
                setReferer(URL);
            }
            return closeableHttpResponse.getEntity();
        } catch (Exception e) {
            closeClient(true);
            e.printStackTrace();
            throw new Exception(msgException(e));
        } finally {
            if (httpPost != null)
                httpPost.setEntity(null);
        }
    }
 
 

    public String request(String url, String utf) throws Exception {
        try {
            utf = StringUtil.isEmpty(utf) ? "utf-8" : utf;
            HttpEntity entity = request(url);
            InputStream inputStream = entity.getContent();
            if (inputStream != null) {
                return readLineInputStream(utf, inputStream);
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new Exception(msgException(e));
        } finally {
            closeHttpResponse();
        }
    }
 

    public byte[] request(String url, Integer numBer) throws Exception {
        try {
            return toByteArrays(request(url).getContent(), numBer);
        } catch (EOFException e) {
            throw new Exception(e.getMessage());
        } finally {
            closeHttpResponse();
        }
    }
 

    public byte[] toByteArrays(InputStream in, Integer numBer) throws Exception {
        numBer = numBer == null ? 8 : numBer;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * numBer];
        int i = 0;
        while ((i = in.read(buffer)) != -1) {
            out.write(buffer, 0, i);
        }
        return out.toByteArray();
    }

    public String readLineInputStream(String utf, HttpEntity httpEntity) throws IOException {
        return readLineInputStream(utf, httpEntity.getContent());
    }
 

    public String readLineInputStream(String utf, InputStream inputStream) throws IOException {
        BufferedReader bf = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(inputStream,
                    utf);
            bf = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bf.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bf.close();
            isr.close();
            inputStream.close();
        }
        return "";
    }
 

    public String readLineInputStream(InputStream inputStream) throws IOException {
        return readLineInputStream("utf-8", inputStream);
    }
 

    public String readLineInputStream(HttpEntity httpEntity) throws Exception {
        try {
            if (httpEntity != null) {
                return readLineInputStream("utf-8", httpEntity.getContent());
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("read fileBuffer Exception:" + e.getMessage());
        }
    }
 

    public void setCookie(String cookie) {
        if (METHOD_NAME.equalsIgnoreCase("get")) {
            httpGet.setHeader("Cookie", cookie);
        } else if (METHOD_NAME.equalsIgnoreCase("post")) {
            httpPost.setHeader("Cookie", cookie);
        }
    }
 
    public void setHeader(String headerName, String headerVal) throws Exception {
        setHeader(new CommonMap<String, String>(headerName, headerVal));
    }
 

    public void setHeader(Map<String, String> header) throws Exception {
        try {
            if (header != null) {
                for (String hd : header.keySet()) {
                    if (METHOD_NAME.equalsIgnoreCase("get")) {
                        httpGet.setHeader(hd, header.get(hd));
                    } else if (METHOD_NAME.equalsIgnoreCase("post")) {
                        httpPost.setHeader(hd, header.get(hd));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("setHeader :" + e.getMessage());
        }
    }

    public void setEntity(String params) throws Exception {
        setEntity(getUrlParams(params));
    }
 

    public void setEntity(Map<String, String> params) throws Exception {
        try {
            if (METHOD_NAME.equalsIgnoreCase("post")) {
                httpPost.setEntity(new ByteArrayEntity(JSONObject.toJSON(params).toString().getBytes("utf-8")));
            }
        } catch (Exception e) {
            throw new Exception("setEntity :" + e.getMessage());
        }
    }

    public void setEntity(StringEntity json) throws Exception {
        try {
            if (METHOD_NAME.equalsIgnoreCase("post")) {
                httpPost.setEntity(json);
 
            }
        } catch (Exception e) {
            throw new Exception("setEntity :" + e.getMessage());
        }
    }
 

    public Header getContentType() throws Exception {
        if (closeableHttpResponse == null || closeableHttpResponse.getEntity() == null) {
            throw new Exception("java.lang.NullPointerException: closeableHttpResponse ");
        }
        return closeableHttpResponse.getEntity().getContentType();
    }
 

    public String getContentType(boolean isFormat) {
        String contentType = "";
        try {
            if (isFormat) {
                contentType = getContentType().toString();
                if (StringUtil.isNotEmpty(contentType)) {
                    int endIndex = contentType.length();
                    if (contentType.indexOf(";") != -1) {
                        endIndex = contentType.indexOf(";");
                    }
                    contentType = StringUtil.isNotEmpty(contentType) ? contentType.substring(contentType.indexOf("/") + 1, endIndex) : "";
                }
            } else {
                return getContentType().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentType;
    }
 

    public int getStatusCode() throws Exception {
        if (closeableHttpResponse == null || closeableHttpResponse.getStatusLine() == null) {
            throw new Exception("java.lang.NullPointerException: closeableHttpResponse 为空,请先初始化对象。");
        }
        try {
            return closeableHttpResponse.getStatusLine().getStatusCode();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
 
    public CloseableHttpResponse getHttpResponse() throws Exception {
        try {
            return closeableHttpResponse;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
 

    public void closeHttpResponse() throws IOException {
        if (closeableHttpResponse != null) {
            closeableHttpResponse.close();
        }
    }

    public void closeHttpResponse(HttpEntity entity) throws IOException {
        if (closeableHttpResponse != null) {
            EntityUtils.consume(entity);
            closeableHttpResponse.close();
        }
    }
 

    public void setCredentials(String userName, String userPwd) throws Exception {
        try {
            if (httpHost == null) {
                throw new Exception("java.lang.NullPointerException: httpHost ");
            }
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(httpHost.getHostName(), httpHost.getPort()),
                    new UsernamePasswordCredentials(userName, userPwd));
            AuthCache authCache = new BasicAuthCache();
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(httpHost, basicAuth);
            httpClientContext = HttpClientContext.create();
            httpClientContext.setCredentialsProvider(credsProvider);
            httpClientContext.setAuthCache(authCache);
        } catch (Exception e) {
            throw new Exception(msgException(e));
        }
    }
 

    public void setHttpHost(String hostVal) throws Exception {
        if (StringUtil.isNotEmpty(hostVal) && hostVal.indexOf(":") != -1) {
            try {
                String ip = hostVal.substring(0, hostVal.indexOf(":"));
                String host = hostVal.substring(hostVal.indexOf(":") + 1);
                if (StringUtil.isNotEmpty(ip) && isIP(ip) && StringUtil.isNotEmpty(host)) {
                    httpHost = new HttpHost(ip, Integer.parseInt(host));
                }
            } catch (Exception e) {
                throw new Exception(msgException(e));
            }
        } else {
            throw new Exception("httphost :127.0.1.1:8888");
        }
    }
 

    private static List<NameValuePair> getNameValuePairs(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(),
                        (String) entry.getValue()));
            }
        }
        return nvps;
    }

    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>(0);
        if (param == null || param.length() <= 0) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length != 0) {
                map.put(p[0], p.length == 2 && p[1] != null && p[1].length() != 0 ? p[1] : "");
            }
        }
        return map;
    }
 

    public boolean isIP(String addr) throws Exception {
        try {
            if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
                return false;
            }

            String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
 
            Pattern pat = Pattern.compile(rexp);
 
            Matcher mat = pat.matcher(addr);
 
            boolean ipAddress = mat.find();
 
            return ipAddress;
        } catch (Exception e) {
            throw new Exception("isIP exception ....." + msgException(e));
        }
    }
 

    public boolean isImage(String html) {
        if (StringUtil.isNotEmpty(html)) {
            if (ImageType.BMP.toString().equalsIgnoreCase(html)
                    || ImageType.gif.toString().equalsIgnoreCase(html)
                    || ImageType.jpeg.toString().equalsIgnoreCase(html)
                    || ImageType.png.toString().equalsIgnoreCase(html)) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
 

    public boolean isImage() {
        try {
            String requestType = getContentType(true);
            if (StringUtil.isNotEmpty(requestType)) {
                return isImage(requestType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 

    public enum ImageType {
        png, gif, jpg, jpeg, BMP,
    }
 

    public static SSLContext createIgnoreVerifySSL() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
 
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }
 
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }
 
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
 
        sc.init(null, new TrustManager[]{trustManager}, null);
 
        return sc;
    }
 

    public String msgException(Exception e) {
        String msg = "";
        if (e != null && e.getStackTrace().length != 0) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            msg = String.format(":%s,:%s,:%s", stackTraceElement.getFileName(), stackTraceElement.getLineNumber(), stackTraceElement.getMethodName());
        }
        return msg;
    }
 
 
}