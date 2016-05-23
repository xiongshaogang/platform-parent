package cn.com.tcxy.dfs;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.aliyun.openservices.oss.model.ObjectMetadata;

public interface DfsService {

    String upload(String key, InputStream is);

    String upload(String key, byte[] bytes);

    String upload(String key, InputStream is, String contentType);

    String upload(String bucketName, String key, String filename)
            throws FileNotFoundException;

    ObjectMetadata getObjectMetadata(String key);

    void download(String key, String filename);
    
    String getDownloadUrl() ;

	String upload(String key, byte[] bytes, String contentType);

}
