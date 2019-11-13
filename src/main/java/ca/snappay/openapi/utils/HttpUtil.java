package com.wiseasy.openapi.utils;


import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final int DEFAULT_TIMEOUT = 30000;

    public HttpUtil() {
    }

    public static String doGet(String url) throws Exception {
        return doGet(url, (Map)null);
    }

    public static String doGet(String url, Map<String, Object> params) throws Exception {
        return doGet(url, (Map)null, params);
    }

    public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        return doGet(url, headers, params, 30000, 30000);
    }

    public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params, int connectTimeout, int readTimeout) throws Exception {
        return doGet(url, headers, params, connectTimeout, readTimeout, "UTF-8");
    }

    public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params, int connectTimeout, int readTimeout, String charset) throws Exception {
        String get_url = buildGetUrl(url, params, charset);
        return doRequest("GET", get_url, headers, (String)null, connectTimeout, readTimeout, charset);
    }

    public static String doPost(String url) throws Exception {
        return doPost(url, (Map)null);
    }

    public static String doPost(String url, Map<String, Object> params) throws Exception {
        return doPost(url, (Map)null, params);
    }

    public static String doPost(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        return doPost(url, headers, params, 30000, 30000);
    }

    public static String doPost(String url, Map<String, Object> headers, Map<String, Object> params, int connectTimeout, int readTimeout) throws Exception {
        return doPost(url, headers, params, connectTimeout, readTimeout, "UTF-8");
    }

    public static String doPost(String url, Map<String, Object> headers, Map<String, Object> params, int connectTimeout, int readTimeout, String charset) throws Exception {
        String data = buildQueryParams(params, charset);
        return doRequest("POST", url, headers, data, connectTimeout, readTimeout, charset);
    }

    public static String doPostStr(String url, Map<String, Object> headers, String data) throws Exception {
        return doPostStr(url, headers, data, 30000, 30000);
    }

    public static String doPostStr(String url, Map<String, Object> headers, String data, int connectTimeout, int readTimeout) throws Exception {
        return doPostStr(url, headers, data, connectTimeout, readTimeout, "UTF-8");
    }

    public static String doPostStr(String url, Map<String, Object> headers, String data, int connectTimeout, int readTimeout, String charset) throws Exception {
        return doRequest("POST", url, headers, data, connectTimeout, readTimeout, charset);
    }

    public static String doPostJson(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        return doPostJson(url, headers, params, 30000, 30000);
    }

    public static String doPostJson(String url, Map<String, Object> headers, Map<String, Object> params, int connectTimeout, int readTimeout) throws Exception {
        return doPostJson(url, headers, params, connectTimeout, readTimeout, "UTF-8");
    }

    public static String doPostJson(String url, Map<String, Object> headers, Map<String, Object> params, int connectTimeout, int readTimeout, String chartset) throws Exception {
        if (headers == null) {
            headers = new HashMap();
        }

        if (!((Map)headers).containsKey("Content-Type")) {
            ((Map)headers).put("Content-Type", "application/json; charset=" + chartset);
        }

        String data = JSON.toJSONString(params);
        return doRequest("POST", url, (Map)headers, data, connectTimeout, readTimeout, chartset);
    }

    public static String doPostFile(String url, Map<String, Object> headers, Map<String, Object> params, Map<String, FileItem> fileParams) throws Exception {
        return doPostFile(url, headers, params, fileParams, 30000, 30000);
    }

    public static String doPostFile(String url, Map<String, Object> headers, Map<String, Object> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout) throws Exception {
        return doPostFile(url, headers, params, fileParams, connectTimeout, readTimeout, "UTF-8");
    }

    public static String doPostFile(String url, Map<String, Object> headers, Map<String, Object> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout, String charset) throws Exception {
        HttpURLConnection http = null;
        InputStream in = null;
        OutputStream out = null;

        String var32;
        try {
            String boundary = String.valueOf(System.currentTimeMillis());
            http = getHttpConnection("POST", url, connectTimeout, readTimeout);
            http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary + ";charset=" + charset);
            if (headers != null && !headers.isEmpty()) {
                Iterator var11 = headers.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry<String, Object> entry = (Entry)var11.next();
                    http.setRequestProperty((String)entry.getKey(), entry.getValue().toString());
                }
            }

            out = http.getOutputStream();
            byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);
            Entry fileEntry;
            Iterator var30;
            if (params != null && !params.isEmpty()) {
                var30 = params.entrySet().iterator();

                while(var30.hasNext()) {
                    fileEntry = (Entry)var30.next();
                    byte[] textBytes = getTextEntry((String)fileEntry.getKey(), String.valueOf(fileEntry.getValue()), charset);
                    out.write(entryBoundaryBytes);
                    out.write(textBytes);
                }
            }

            if (fileParams != null && !fileParams.isEmpty()) {
                var30 = fileParams.entrySet().iterator();

                while(var30.hasNext()) {
                    fileEntry = (Entry)var30.next();
                    FileItem fileItem = (FileItem)fileEntry.getValue();
                    byte[] fileBytes = getFileEntry((String)fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
                    out.write(entryBoundaryBytes);
                    out.write(fileBytes);
                    out.write(fileItem.getContent());
                }
            }

            byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
            out.write(endBoundaryBytes);
            out.flush();
            in = http.getInputStream();
            var32 = getStreamAsString(in, charset);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception var27) {
                var27.printStackTrace();
            }

            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception var26) {
                var26.printStackTrace();
            }

            try {
                if (http != null) {
                    http.disconnect();
                }
            } catch (Exception var25) {
                var25.printStackTrace();
            }

        }

        return var32;
    }

    public static String doRequest(String method, String url, Map<String, Object> headers, String data, int connectTimeout, int readTimeout, String charset) throws Exception {
        HttpURLConnection http = null;
        InputStream in = null;
        OutputStream out = null;

        String var25;
        try {
            http = getHttpConnection(method, url, connectTimeout, readTimeout);
            if (headers != null && !headers.isEmpty()) {
                Iterator var10 = headers.entrySet().iterator();

                while(var10.hasNext()) {
                    Entry<String, Object> entry = (Entry)var10.next();
                    http.setRequestProperty((String)entry.getKey(), entry.getValue().toString());
                }
            }

            if (data != null && !data.trim().isEmpty()) {
                out = http.getOutputStream();
                out.write(data.getBytes(charset));
                out.flush();
            }

            in = http.getInputStream();
            var25 = getStreamAsString(in, charset);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception var23) {
                var23.printStackTrace();
            }

            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception var22) {
                var22.printStackTrace();
            }

            try {
                if (http != null) {
                    http.disconnect();
                }
            } catch (Exception var21) {
                var21.printStackTrace();
            }

        }

        return var25;
    }

    private static HttpURLConnection getHttpConnection(String method, String url, int connectTimeout, int readTimeout) throws Exception {
        boolean isSSL = url.startsWith("https");
        if (isSSL) {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(new KeyManager[0], new TrustManager[]{new HttpUtil.SimpleTrustManager()}, new SecureRandom());
            SSLSocketFactory sslf = sslContext.getSocketFactory();
            HttpsURLConnection https = (HttpsURLConnection)(new URL(url)).openConnection();
            https.setHostnameVerifier(new HttpUtil.SimpleHostnameVerifier());
            https.setSSLSocketFactory(sslf);
            https.setRequestMethod(method);
            https.setDoOutput(true);
            https.setDoInput(true);
            https.setUseCaches(false);
            https.setConnectTimeout(connectTimeout);
            https.setReadTimeout(readTimeout);
            return https;
        } else {
            HttpURLConnection http = (HttpURLConnection)(new URL(url)).openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setUseCaches(false);
            http.setConnectTimeout(connectTimeout);
            http.setReadTimeout(readTimeout);
            return http;
        }
    }

    private static String getStreamAsString(InputStream in, String charset) throws Exception {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(in, charset));
            StringBuilder buffer = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String var5 = buffer.toString();
            return var5;
        } finally {
            if (reader != null) {
                reader.close();
            }

        }
    }

    private static String buildGetUrl(String url, Map<String, Object> params, String charset) throws Exception {
        String queryParams = buildQueryParams(params, charset);
        if (null != queryParams && !queryParams.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            if (url.endsWith("?")) {
                sb.append(url).append(queryParams);
            } else {
                sb.append(url).append("?").append(queryParams);
            }

            return sb.toString();
        } else {
            return url;
        }
    }

    private static String buildQueryParams(Map<String, Object> params, String charset) throws Exception {
        if (null != params && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            boolean hasParam = false;
            Iterator var4 = params.entrySet().iterator();

            while(var4.hasNext()) {
                Entry<String, Object> entry = (Entry)var4.next();
                Object v = entry.getValue();
                if (v != null) {
                    if (hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }

                    query.append((String)entry.getKey()).append("=").append(URLEncoder.encode(v.toString(), charset));
                }
            }

            return query.toString();
        } else {
            return null;
        }
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws Exception {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data; name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws Exception {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data; name=\"");
        entry.append(fieldName);
        entry.append("\"; filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    private static class SimpleTrustManager implements X509TrustManager {
        private SimpleTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static class SimpleHostnameVerifier implements HostnameVerifier {
        private SimpleHostnameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}

