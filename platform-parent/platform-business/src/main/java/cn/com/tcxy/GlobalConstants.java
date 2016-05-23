package cn.com.tcxy;


/**
 * 系统常量类
 *
 */
public class GlobalConstants {

    public static final String CURRENT_USER = "currentUser";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";
    
    /**
     * 登录状态:成功,
     */
    public static final String LOGIN_SUCCESS = "1";
    /**
     * 登录状态:失败,
     */
    public static final String LOGIN_FAIL = "0";
    
    /**
     * 用户签名(令牌)的有效期时长,单位为秒(s),
     */
    public static final int ACCESS_TOKEN_TIMEOUT = 7200;
    /**
     * 用户签名(令牌)字符串的分隔符,
     */
    public static final String ACCESS_TOKEN_SEPARATOR = ".";
    /**
     * 用户签名(令牌)字符串由5部分组成, applicationId,md5加密字符串,有效期时长,有效期截止时间戳,租户关联的用户id,
     * "access_token": "appId.8D9F1B4DC98F945555E1C4652004FBF5.7200.1409022222777.userId",
     */
    public static final int ACCESS_TOKEN_PART_COUNT = 5;
    
    public static final String REQUEST_PATH ="requestPath";
}
