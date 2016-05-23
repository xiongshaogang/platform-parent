package cn.com.tcxy.dfs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.test.BaseSpringTest;
import cn.com.tcxy.util.ImageUtil;

public class OSSDfsServiceTest extends BaseSpringTest{
	
	@Autowired
	private OSSDfsService oSSDfsService;
	
	@Test
    public void upload() throws IOException {

		byte[] bytes = getBytes("E:\\1.jpg");
		byte[] thumbs;
        try {
            thumbs = ImageUtil.resize(bytes, 160, 160);
            this.oSSDfsService.upload("user/1.jpg", new ByteArrayInputStream(bytes));
            this.oSSDfsService.upload("user/thumbs/1.jpg", new ByteArrayInputStream(thumbs));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void download() {

    }

    /** 
     * 获得指定文件的byte数组 
     */  
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
    
    
    
}
