package ca.snappay.openapi.utils;

/**
 * @Auther: liqie
 * @Date: 2018/11/4 16:30
 * @Description:
 */
public class Constants {

    // 应用ID
    public static final String APP_ID = "app_id";

    // 请求方法
    public static final String METHOD = "method";

    // 请求数据格式
    public static final String FORMAT = "format";

    // 请求使用的编码格式
    public static final String CHARSET = "charset";

    // 商户生成签名字符串所使用的签名算法类型,目前支持RSA和MD5，推荐使用RSA
    public static final String SIGN_TYPE = "sign_type";

    // 签名
    public static final String SIGN = "sign";

    // 版本
    public static final String VERSION = "version";

    // 发送请求的时间,和标准时间前后不能超过15分钟
    public static final String TIMESTAMP = "timestamp";

    // 请求数据格式: JSON
    public static final String FORMAT_JSON = "JSON";

    // 编码格式
    public static final String CHARSET_UTF8 = "UTF-8";

    // 算法类型 MD5
    public static final String SIGN_TYPE_MD5 = "MD5";

    // 算法类型 RSA
    public static final String SIGN_TYPE_RSA = "RSA";

    // 版本 1.0
    public static final String VERSION_1 = "1.0";

    // 状态码，0 表示请求成功，其他表示失败
    public static final String CODE = "code";

    // 错误信息
    public static final String MSG = "msg";

    // 返回数据总数
    public static final String TOTAL = "total";

    // 返回数据
    public static final String DATA = "data";

    // 接口序列号
    public static final String PSN = "psn";

    // 状态码，0:成功
    public static final String CODE_SUCCESS = "0";

    // 语言支持, Accept-Language：客户端可接受语言，如：en-US,zh-CN等，目前中文，英文，该值通过http header进行传递
    public static final String ACCEPT_LANGUAGE_US = "en-US";

    public static final String ACCEPT_LANGUAGE_CN = "zh-CN";

    // 各环境开放服务网关服务器地址
    public static final String OSG_SERVER_URL_DEV = "http://open-snp.wangtest.cn/api/gateway";

    public static final String OSG_SERVER_URL_TEST = "http://open-snappay.ca/api/gateway";

    public static final String OSG_SERVER_URL_PROD = "https://open.snappay.com/api/gateway";

}
