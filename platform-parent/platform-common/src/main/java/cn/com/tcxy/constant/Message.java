package cn.com.tcxy.constant;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.tcxy.core.HttpHeader;

/**
 * 消息统一管理类
 * 
 * @author xieyingbin
 *
 */
public class Message {
    private static final Logger LOG = LoggerFactory.getLogger(Message.class);

    /**
     * 小云健康平台接口返回错误码分类： 操作成功:2000： 程序正常的处理了客户端的请求； 业务类错误:3xxx：
     * 程序执行中断，存在不符合业务规则的错误，如:账号不存在，密码不正确，账号重复等； 程序类错误:4xxx： 如:参数为空，参数格式不正确；
     * 服务执行错误:5xxx：程序执行非正常结束，主线业务处理失败，如数据库连接失败，图片处理失败，图片上传失败等错误；
     * 正常的中英文字符翻译(文案信息):6xxx
     * 
     * 消息文案文件：message_zh.properties、message_en.properties
     */

    /**
     * 操作成功
     */
    public static final String M2000 = "2000";
    /**
     * 正常的中英文字符翻译(文案信息):6xxx
     */
    public static final String M6000 = "6000";
    public static final String M6001 = "6001";
    public static final String M6002 = "6002";
    /**
     * 服务执行错误:5xxx
     */
    public static final String M5000 = "5000";
    public static final String M5001 = "5001";
    public static final String M5002 = "5002";
    public static final String M5003 = "5003";
    public static final String M5004 = "5004";
    public static final String M5005 = "5005";
    public static final String M5006 = "5006";
    public static final String M5007 = "5007";
    public static final String M5009 = "5009";
    public static final String M5010 = "5010";

    /**
     * 程序类错误:4xxx
     */
    public static final String M4000 = "4000";
    public static final String M4001 = "4001";
    public static final String M4002 = "4002";
    public static final String M4003 = "4003";
    public static final String M4004 = "4004";
    public static final String M4005 = "4005";
    public static final String M4006 = "4006";
    public static final String M4007 = "4007";
    public static final String M4008 = "4008";
    public static final String M4009 = "4009";

    /**
     * 业务类错误:3xxx
     */
    public static final String M3001 = "3001";
    public static final String M3002 = "3002";
    public static final String M3003 = "3003";
    public static final String M3004 = "3004";
    public static final String M3005 = "3005";
    public static final String M3006 = "3006";
    public static final String M3007 = "3007";
    public static final String M3009 = "3009";
    public static final String M3010 = "3010";
    public static final String M3011 = "3011";
    public static final String M3012 = "3012";
    public static final String M3013 = "3013";
    public static final String M3014 = "3014";
    public static final String M3015 = "3015";
    public static final String M3016 = "3016";
    public static final String M3017 = "3017";
    public static final String M3018 = "3018";
    public static final String M3019 = "3019";
    public static final String M3020 = "3020";
    public static final String M3021 = "3021";
    public static final String M3022 = "3022";
    public static final String M3023 = "3023";
    public static final String M3025 = "3025";
    public static final String M3026 = "3026";
    public static final String M3027 = "3027";
    public static final String M3028 = "3028";
    public static final String M3029 = "3029";
    public static final String M3030 = "3030";
    public static final String M3031 = "3031";
    public static final String M3032 = "3032";
    public static final String M3033 = "3033";
    public static final String M3034 = "3034";
    public static final String M3035 = "3035";
    public static final String M3036 = "3036";
    public static final String M3037 = "3037";
    public static final String M3038 = "3038";
    public static final String M3039 = "3039";
    public static final String M3040 = "3040";
    public static final String M3041 = "3041";
    public static final String M3042 = "3042";
    public static final String M3043 = "3043";
    public static final String M3045 = "3045";
    public static final String M3046 = "3046";
    public static final String M3047 = "3047";
    public static final String M3048 = "3048";
    public static final String M3049 = "3049";
    public static final String M3050 = "3050";
    public static final String M3052 = "3052";
    public static final String M3053 = "3053";
    public static final String M3054 = "3054";
    public static final String M3055 = "3055";
    public static final String M3056 = "3056";
    public static final String M3057 = "3057";
    public static final String M3058 = "3058";
    public static final String M3059 = "3059";
    public static final String M3060 = "3060";
    public static final String M3061 = "3061";
    public static final String M3062 = "3062";
    public static final String M3063 = "3063";
    public static final String M3064 = "3064";
    public static final String M3065 = "3065";
    public static final String M3067 = "3067";
    public static final String M3068 = "3068";
    public static final String M3069 = "3069";
    public static final String M3070 = "3070";
    public static final String M3071 = "3071";
    public static final String M3072 = "3072";
    public static final String M3073 = "3073";
    public static final String M3074 = "3074";
    public static final String M3075 = "3075";
    public static final String M3076 = "3076";
    public static final String M3077 = "3077";
    public static final String M3078 = "3078";
    public static final String M3079 = "3079";
    // 新添加
    public static final String M3080 = "3080";
    public static final String M3081 = "3081";
    public static final String M3082 = "3082";
    public static final String M3083 = "3083";
    public static final String M3084 = "3084";
    public static final String M3085 = "3085";
    public static final String M3086 = "3086";
    public static final String M3087 = "3087";
    public static final String M3088 = "3088";
    public static final String M3089 = "3089";
    public static final String M3090 = "3090";
    public static final String M3091 = "3091";
    public static final String M3092 = "3092";
    public static final String M3093 = "3093";
    public static final String M3094 = "3094";
    public static final String M3095 = "3095";
    public static final String M3096 = "3096";
    public static final String M3097 = "3097";
    public static final String M3098 = "3098";
    public static final String M3099 = "3099";
    public static final String M3100 = "3100";
    public static final String M3101 = "3101";
    public static final String M3102 = "3102";
    public static final String M3103 = "3103";
    public static final String M3104 = "3104";
    public static final String M3105 = "3105";
    public static final String M3106 = "3106";
    public static final String M3107 = "3107";
    public static final String M3108 = "3108";
    public static final String M3109 = "3109";
    public static final String M3110 = "3110";
    public static final String M3111 = "3111";
    public static final String M3112 = "3112";
    public static final String M3113 = "3113";
    public static final String M3114 = "3114";
    public static final String M3115 = "3115";
    public static final String M3116 = "3116";
    public static final String M3117 = "3117";
    public static final String M3118 = "3118";
    public static final String M3119 = "3119";
    public static final String M3120 = "3120";
    public static final String M3121 = "3121";
    public static final String M3122 = "3122";
    public static final String M3123 = "3123";
    public static final String M3124 = "3124";
    public static final String M3125 = "3125";
    public static final String M3126 = "3126";
    public static final String M3127 = "3127";
    public static final String M3128 = "3128";
    public static final String M3129 = "3129";
    public static final String M3130 = "3130";
    public static final String M3131 = "3131";
    public static final String M3132 = "3132";
    public static final String M3133 = "3133";
    public static final String M3134 = "3134";
    public static final String M3135 = "3135";
    public static final String M3136 = "3136";
    public static final String M3137 = "3137";
    public static final String M3138 = "3138";
    public static final String M3139 = "3139";
    public static final String M3140 = "3140";
    public static final String M3141 = "3141";
    public static final String M3142 = "3142";
    public static final String M3143 = "3143";
    public static final String M3144 = "3144";
    public static final String M3145 = "3145";
    public static final String M3146 = "3146";
    public static final String M3147 = "3147";
    public static final String M3148 = "3148";
    public static final String M3149 = "3149";
    public static final String M3150 = "3150";
    public static final String M3151 = "3151";
    public static final String M3152 = "3152";
    public static final String M3153 = "3153";
    public static final String M3154 = "3154";
    public static final String M3155 = "3155";
    public static final String M3156 = "3156";
    public static final String M3157 = "3157";
    public static final String M3158 = "3158";
    public static final String M3159 = "3159";
    public static final String M3160 = "3160";
    public static final String M3161 = "3161";
    public static final String M3162 = "3162";
    public static final String M3163 = "3163";
    public static final String M3164 = "3164";
    public static final String M3165 = "3165";
    public static final String M3166 = "3166";
    public static final String M3167 = "3167";
    public static final String M3168 = "3168";
    public static final String M3169 = "3169";
    public static final String M3170 = "3170";
    public static final String M3171 = "3171";
    public static final String M3172 = "3172";
    public static final String M3173 = "3173";
    public static final String M3174 = "3174";
    public static final String M3175 = "3175";
    public static final String M3176 = "3176";
    public static final String M3177 = "3177";
    public static final String M3178 = "3178";
    public static final String M3179 = "3179";
    public static final String M3180 = "3180";
    public static final String M3181 = "3181";
    public static final String M3190 = "3190";
    public static final String M3191 = "3191";
    public static final String M3192 = "3192";
    public static final String M3193 = "3193";
    public static final String M3194 = "3194";
    public static final String M3195 = "3195";
    public static final String M3196 = "3196";
    public static final String M3197 = "3197";
    public static final String M3198 = "3198";

    private static Properties PROP_ZH = new Properties();
    private static Properties PROP_EN = new Properties();

    static {
        loads();
    }

    /**
     * 加载消息初始化信息
     */
    synchronized static public void loads() {
        LOG.info("载入消息信息   开始");
        InputStream is1 = Message.class.getResourceAsStream("/message_zh.properties");
        try {
            PROP_ZH.load(is1);
            is1.close();
        } catch (Exception e) {
            // System.err.println("不能读取属性文件. ");
            LOG.error("不能读取属性文件.");
        }

        InputStream is2 = Message.class.getResourceAsStream("/message_en.properties");
        try {
            PROP_EN.load(is2);
            is2.close();
        } catch (Exception e) {
            // System.err.println("不能读取属性文件. ");
            LOG.error("不能读取属性文件.");
        }
        LOG.info("载入消息信息   结束");
    }

    /**
     * 根据消息code获取消息文案信息
     * 
     * @param code
     *            消息code
     * @return 消息文案
     */
    public static String getMessage(String code) {
        Thread current = Thread.currentThread();
        LOG.info("$$$$$$$$ getMessage Thread id=" + current.getId() + "  name=" + current.getName());

        boolean isEnglish = false;
        HttpHeader header = HttpHeader.get();
        if (header != null) {
            LOG.info("$$$$$$$$ getMessage header=" + header.toString());
            isEnglish = header.isLanguage(HttpHeader.LANGUAGE_EN);
        }

        LOG.info("$$$$$$$$ getMessage isEnglish=" + isEnglish);

        String msg = "";
        if (isEnglish) {
            // 英文
            msg = String.valueOf(PROP_EN.get(code));
        } else {
            // 中文
            msg = String.valueOf(PROP_ZH.get(code));
        }
        return msg;
    }

    /**
     * 可传递参数，并根据消息code获取消息文案信息
     * 
     * @param code
     *            消息code
     * @param args
     *            消息参数
     * @return 消息文案
     */
    public static String getMessage(String code, Object... args) {
        String msg = MessageFormat.format(getMessage(code), args);
        return msg;
    }
}
