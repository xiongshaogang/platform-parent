package cn.com.tcxy.dfs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

/**
 * 封装阿里oss服务
 * 
 * @author Johnny Wang
 * 
 */
@Component("ossService")
public class OSSDfsService implements DfsService, InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(OSSDfsService.class);

    /**
     * 访问的阿里云路径
     */
    @Value("#{ dfsProperties['oss.endpoint'] }")
    private String ossEndpoint;

    /**
     * 阿里云oss使用的accessId
     */
    @Value("#{ dfsProperties['oss.access.id'] }")
    private String ossAccessId;

    /**
     * 阿里云oss使用的ossAccessKey
     */
    @Value("#{ dfsProperties['oss.access.key'] }")
    private String ossAccessKey;

    /**
     * bucket名称
     */
    @Value("#{ dfsProperties['oss.default.bucket'] }")
    private String defaultBucket;

    /**
     * 允许打开的最大HTTP连接数。默认为50
     */
    @Value("#{ dfsProperties['oss.max.connection'] }")
    private int ossMaxConnection;

    /**
     * 可重试的请求失败后最大的重试次数。默认为3次
     */
    @Value("#{ dfsProperties['oss.max.error.retry'] }")
    private int ossMaxErrorRetry;

    /**
     * 建立连接的超时时间（单位：毫秒）。默认为50000毫秒
     */
    @Value("#{ dfsProperties['oss.connection.timeout'] }")
    private int ossConnectionTimeout;

    /**
     * 通过打开的连接传输数据的超时时间（单位：毫秒）。默认为50000毫秒
     */
    @Value("#{ dfsProperties['oss.socket.timeout'] }")
    private int ossSocketTimeout;

    private ClientConfiguration conf;

    @Override
    public void afterPropertiesSet() {
        LOGGER.info("初始化oss服务");
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(ossConnectionTimeout);
        conf.setMaxConnections(ossMaxConnection);
        conf.setMaxErrorRetry(ossMaxErrorRetry);
        conf.setSocketTimeout(ossSocketTimeout);
        LOGGER.info("初始化完成");
    }

    @Override
    public ObjectMetadata getObjectMetadata(String key) {
        return getOSSClient().getObjectMetadata(defaultBucket, key);
    }

    private OSSClient getOSSClient() {
        return new OSSClient(ossEndpoint, ossAccessId, ossAccessKey, conf);
    }

    private String uploadFile(String bucketName, String key,
            ObjectMetadata objectMeta, InputStream input) {

        PutObjectResult result = getOSSClient().putObject(bucketName, key,
                input, objectMeta);

        return result.getETag();
    }

    @Override
    public String upload(String key, InputStream is) {
        try {
            byte[] bytes = IOUtils.toByteArray(is);

            return this.upload(key, bytes);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return "";
        }
    }

    @Override
    public String upload(String key, byte[] bytes) {

        // 必须在这里做转换,不然无法成功上传
        InputStream input = new ByteArrayInputStream(bytes);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(bytes.length);
        objectMeta.setContentType("image/jpeg");

        return uploadFile(defaultBucket, key, objectMeta, input);
    }
    
    @Override
    public String upload(String key, byte[] bytes,String contentType) {

        // 必须在这里做转换,不然无法成功上传
        InputStream input = new ByteArrayInputStream(bytes);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(bytes.length);
        if(contentType!=null){
        	objectMeta.setContentType(contentType);
        }

        return uploadFile(defaultBucket, key, objectMeta, input);
    }

    @Override
    public String upload(String bucketName, String key, String filename)
            throws FileNotFoundException {

        File file = new File(filename);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);

        return uploadFile(bucketName, key, objectMeta, input);
    }

    @Override
    public void download(String key, String filename) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(defaultBucket, key);
        File file = new File(filename);
        getOSSClient().getObject(getObjectRequest, file);
    }

    @Override
    public String upload(String key, InputStream is, String contentType) {
        byte[] bytes = new byte[] {};

        try {
            bytes = IOUtils.toByteArray(is);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return "";
        }

        // 必须在这里做转换,不然无法成功上传
        InputStream input = new ByteArrayInputStream(bytes);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(bytes.length);
        objectMeta.setContentType(contentType);

        return uploadFile(defaultBucket, key, objectMeta, input);
    }

    /**
     * 获取下载url地址
     * 
     */
    @Override
    public String getDownloadUrl() {
        return ossEndpoint + "/" + defaultBucket;
    }
}
