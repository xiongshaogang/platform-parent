package cn.com.tcxy.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONObject;

public class HttpsClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpsClientUtil.class);
    public static void main(String[] args) throws Exception {
        // Date date = new Date();
        // String txnTime = DateFormatUtils.format(date,
        // DateUtil.PURE_STANDARD_DATE_TIME_FORMAT_STR);
        // System.out.println(txnTime);

        String token = "c47ec4db451a8d330e8325d1868f4901";
        String account = "05e3c540f49c11e48ad3ac853d9f54f2";
        String res = account + token + "20150923162030";
        String base = account + ":" + "20150923162030";
        String md5Sign = MD5Sign(res);
        String url = "https://app.cloopen.com:8883/2013-12-26/SubAccounts/05e3c540f49c11e48ad3ac853d9f54f2/Group/CreateGroup?sig="
                + md5Sign;
        Map<String, String> head = new HashMap<String, String>();
        head.put("Authorization", base64(base));
        head.put("Content-Type", "application/xml");
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "天创医疗群");
        params.put("type", "1");
        params.put("permission", "0");
        params.put("declared", "test");
        // String result = postForJsonResult(url, head, params);
        String xml = createRequestXML(params);
        System.out.println(xml);
        String result = postForxmlResult(url, head, xml);
        System.out.println("结果：" + result);
        // System.out.println(createXML());
    }

    public static String MD5Sign(String res) throws Exception {
        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        // String token = "c47ec4db451a8d330e8325d1868f4901";
        // String account = "05e3c540f49c11e48ad3ac853d9f54f2";
        // String res = account + token + "20150923162030";
        byte[] md5Bytes = md5.digest(res.getBytes());
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        System.out.println("retrun:" + hexValue.toString());
        return hexValue.toString();
    }

    public static String base64(String base) {
        BASE64Encoder encoder = new BASE64Encoder();
        // String account = "05e3c540f49c11e48ad3ac853d9f54f2";
        // String time = "20150923162030";
        // String en = account + ":" + time;
        String result = encoder.encode(base.getBytes());
        return result;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }

                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                        throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub
                    return false;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    public static String postForJsonResult(String url, Map<String, String> head, Map<String, String> params)
            throws Exception {
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpPost post = new HttpPost();
        if (url == null) {
            throw new Exception("url为空");
        }
        post.setURI(new URI(url));
        if (head != null && head.keySet() != null) {
            for (String key : head.keySet()) {
                post.addHeader(key, head.get(key));
            }
        }
        JSONObject jsonParam = new JSONObject();
        if (params != null && params.keySet() != null) {
            for (String key : params.keySet()) {
                jsonParam.put(key, params.get(key));
            }
        }
        // post.addHeader("Authorization", base64()); // 认证token
        post.addHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = br.readLine();
        StringBuffer buffer = new StringBuffer();
        String result = null;
        while (line != null) {
            buffer.append(line);
            line = br.readLine();
        }
        if (buffer != null) {
            result = buffer.toString();
        }
        return result;
    }

    public static String postForxmlResult(String url, Map<String, String> head, String xmlRequest) throws Exception {
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpPost post = new HttpPost();
        if (url == null) {
            throw new Exception("url为空");
        }
        post.setURI(new URI(url));
        if (head != null && head.keySet() != null) {
            for (String key : head.keySet()) {
                post.addHeader(key, head.get(key));
            }
        }
        JSONObject jsonParam = new JSONObject();
        // post.addHeader("Authorization", base64()); // 认证token
        // post.addHeader("Content-Type", "application/xml");
        StringEntity entity = new StringEntity(xmlRequest.toString(), "utf-8");// 解决中文乱码问题
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = br.readLine();
        StringBuffer buffer = new StringBuffer();
        String result = null;
        while (line != null) {
            buffer.append(line);
            line = br.readLine();
        }
        if (buffer != null) {
            result = buffer.toString();
        }
        return result;
    }

    public static String createRequestXML(Map<String, String> params) {
        String xmlStr = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlVersion("1.0");
            Element root = document.createElement("Request");
            document.appendChild(root);
            if (params != null && params.keySet() != null) {
                for (String key : params.keySet()) {
                    Element name = document.createElement(key);
                    name.setTextContent(params.get(key));
                    root.appendChild(name);
                }
            }
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            // export string
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            /**
             * 原来注释的,现在打开这个注释
             * @author llr
             */
            // transFormer.transform(domSource, new StreamResult(bos));
            transFormer.transform(domSource, new StreamResult(bos));
            xmlStr = bos.toString();// 结果
            // save as file
            // File file = new File("TelePhone.xml");
            // if (!file.exists()) {
            // file.createNewFile();
            // }
            // FileOutputStream out = new FileOutputStream(file);
            // StreamResult xmlResult = new StreamResult(out);
            // transFormer.transform(domSource, xmlResult);
            // --------
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("xml:"+xmlStr);
        return xmlStr;
    }
}
