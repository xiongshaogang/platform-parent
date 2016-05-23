package cn.com.tcxy.core;

import org.apache.commons.lang.StringUtils;

/**
 * 保存API请求头部信息至本次请求线程，请求线程内共享该信息。
 * 
 * @author xieyingbin
 *
 */
public class HttpHeader {
    public static final String LANGUAGE_ZH = "zh";
    public static final String LANGUAGE_EN = "en";

    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String DEVICE_MODEL = "Device-Model";
    public static final String DEVICE_BRAND = "Device-Brand";
    public static final String SYSTEM_TYPE = "System-Type";
    public static final String SYSTEM_VERSION = "System-Version";
    public static final String APP_TYPE = "App-Type";
    public static final String APP_VERSION = "App-Version";
    public static final String TOKEN = "Token";
    public static final String IP = "IP";

    /**
     * 请求线程内共享对象
     */
    public static ThreadLocal<HttpHeader> local = new ThreadLocal<HttpHeader>();

    private String acceptLanguage;
    private String deviceModel;
    private String deviceBrand;
    private String systemType;
    private String systemVersion;
    private String appType;
    private String appVersion;
    private String token;
    private String ip;

    public static void set(HttpHeader header) {
        local.set(header);
    }

    public static HttpHeader get() {
        return local.get();
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 查看header的是否包含指定语言信息
     * 
     * @param language
     *            语言信息（zh: 中文， en: 英文）
     * 
     * @return true: 包含, false: 不包含
     */
    public boolean isLanguage(String language) {
        if (StringUtils.contains(acceptLanguage, language)) {
            return true;
        }
        return false;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        String EQ = "=";
        String SEM = ";";
        StringBuilder sb = new StringBuilder();
        sb.append(ACCEPT_LANGUAGE).append(EQ).append(acceptLanguage).append(SEM);
        sb.append(DEVICE_MODEL).append(EQ).append(deviceModel).append(SEM);
        sb.append(DEVICE_BRAND).append(EQ).append(deviceBrand).append(SEM);
        sb.append(SYSTEM_TYPE).append(EQ).append(systemType).append(SEM);
        sb.append(SYSTEM_VERSION).append(EQ).append(systemVersion).append(SEM);
        sb.append(APP_TYPE).append(EQ).append(appType).append(SEM);
        sb.append(APP_VERSION).append(EQ).append(appVersion).append(SEM);
        sb.append(TOKEN).append(EQ).append(token).append(SEM);

        return sb.toString();
    }

}
