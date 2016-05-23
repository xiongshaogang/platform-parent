package cn.com.tcxy.core;

import java.util.HashMap;
import java.util.Map;

import cn.com.tcxy.constant.Message;

/**
 * 留给web层的controller使用，以json格式传递数据app等前端
 * 
 * @author Johnny Wang
 *
 * @param <T>
 *            data字段的类型
 */

public class JsonResult {

    public static final String DEPRECATED_STATUS = "-1";

    public static final String SUCCESS_STATUS = "2000";

    public static final String FAIL_STATUS = "5000";

    /**
     * 模块名称
     */
    private String module;

    /** 状态码 */
    private String code;

    /** 通知消息 */
    private String message;

    /** 数据 */
    private Object data;

    /**
     * 额外数据：比如分页信息等
     */
    private Map<String, Object> extraData = new HashMap<String, Object>();

    // /**
    // * TODO:最终使用data字段来代替
    // */
    // @Deprecated
    // private Object result;

    public JsonResult() {
        this(Boolean.TRUE);
    }

    public JsonResult(boolean success) {
        // this(success, "");
        this.code = success ? "2000" : "5000";
        this.message = Message.getMessage(this.code);
        this.data = null;
    }

    public JsonResult(boolean success, Object data) {
        // this(success, "", data);
        this.code = success ? "2000" : "5000";
        this.message = Message.getMessage(this.code);
        this.data = data;
    }

    /**
     * （不支持多语言化: 废止）
     * 
     * @param success
     * @param message
     */
//    @Deprecated
//    public JsonResult(boolean success, String message) {
//        this(success, message, null);
//    }

    /**
     * 接口返回对象信息  (不支持多语言化)
     * 
     * @param success
     * @param message
     * @param data
     */
    public JsonResult(boolean success, String message, Object data) {
        this.code = success ? "2000" : "5000";
        this.message = message;
        this.data = data;
    }

    /**
     * 
     * @param code
     */
    public JsonResult(String code) {
        // this(code, Message.getMessage(code));
        this.code = code;
        this.message = Message.getMessage(code);
        this.data = null;
    }

    /**
     * 
     * @param code
     * @param data
     */
    public JsonResult(String code, Object data) {
        // this(code, Message.getMessage(code), data);
        this.code = code;
        this.message = Message.getMessage(code);
        this.data = data;

    }

    /**
     * （不支持多语言化: 废止）
     * 
     * @param code
     * @param message
     */
//    @Deprecated
//    public JsonResult(String code, String message) {
//        this(code, message, null);
//    }
 
    /**
     * 接口返回对象信息  (不支持多语言化)
     * 
     * @param code
     * @param message
     * @param data
     */
    public JsonResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void addExtra(String key, Object value) {
        if (this.extraData == null) {
            this.extraData = new HashMap<String, Object>();
        }
        this.extraData.put(key, value);
    }

    public boolean isSuccess() {
        return this.code.equals("2000");
    }

    // public Object getResult() {
    // return result;
    // }
    //
    // public void setResult(Object result) {
    // this.result = result;
    // }

    @Override
    public String toString() {
        return "JsonResult [module=" + module + ", code=" + code + ", message=" + message + ", data=" + data
                + ", extraData=" + extraData + "]";
    }

}
