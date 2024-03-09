package com.example.common.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author : BG547563
 */
public class QySignUtil {

    private final String k = "15112637765";

    private final String s = "2AD1222AD2D9ED77F571EBBCBC90CA2E";

    public static final String EQUAL_SIGN = "=";
    public static final String APP_ID = "appId";
    public static final String SERVICE_TYPE = "serviceType";
    public static final String BIZ_PARAM = "bizParam";
    public static final String TIMESTAMP = "timestamp";

    public static String calcSign(final String appId, final String bizParam, final String serviceType, final Long timestamp,
                                  final String secret) throws UnsupportedEncodingException {
        byte[] bytes = buildSignStr(appId, bizParam, serviceType, timestamp, secret)
                .getBytes(StandardCharsets.UTF_8);
        return new String(DigestUtils.md5DigestAsHex(bytes).getBytes(StandardCharsets.UTF_8));
    }

    private static String buildSignStr(final String appId, final String bizParam, final String serviceType, final Long timestamp,
                                       final String secret) {
        return APP_ID + EQUAL_SIGN + appId +
                BIZ_PARAM + EQUAL_SIGN + bizParam +
                SERVICE_TYPE + EQUAL_SIGN + serviceType +
                TIMESTAMP + EQUAL_SIGN + timestamp +
                secret;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        long l = System.currentTimeMillis();
        System.out.println(l);
        String querySalesOrderList = calcSign("xxx",
                "{\"page\":1,\"pageSize\":1,\"updateTimeFrom\":\"2024-02-04 00:00:00\",\"updateTimeTo\":\"2024-03-03 14:00:00\",\"orderByParam\":\"update_time\",\"orderByOrder\":\"desc\",\"includesSoftDel\":0,}"
                , "QUERY_SALES_ORDER_LIST", l, "xxx");
        System.out.println(querySalesOrderList);
    }
}
