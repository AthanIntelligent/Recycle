package com.jack.recycle.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102600763270";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVbZeOMxodq+NGdb6hnX9qZocJCzCY3XcQplTW1EnlbXSjJRj1CRnFU8yHw0JeM9vbOoG06Jn8iit3P4Wov0kwhHfc7U90/gPoe50SqcIYtnQMIiI1FEkg7Lsh1XVI+ujdzadAs8zQlksEGtj4yueXS8E/DQviPTdQLkdIvr2sG/2n0AMbSIzA+uvHggd3iypoTkERbGDuDyWrUCAnx5OmWXdvq2JYrGuVo9JMEXkmHxO8hYP4GRE9KwpjEat4kl2wSfzEHJL/eIdVfkU3Da0LuwrI2F+Y8wyFDlsjrYhPFtM38oXLa27nesicq2ZYw4nMrl+Ffno8lRfJ2eBr4t05AgMBAAECggEAA2512YUg52eIG5kenJWCVI4wMav5HLmRP2eicSCE5ejikdCLRy1DUz2+Z6u7PXCRbvJkAiSJTLxbjSx/REXIR33zRraIaZZfvpoAHGqbk3e1KIYsJO3nVAM7tOYzAE8Kw/UyDxFR9cRNyrVXIDeAo//QW0NQb4yaVZQKfQgsUOR2mkacYIYpL88c7yc8FNqEtVngonxMusUB3WC+ajmlW4l7NA1ftHS6hIOEXKwtTFejbKKBDP6d6YsMdcREzPhceC5YiCAMP3Qev+a0p6GCAR3hQ/QpD9oi/SDgQyUKEfWeu4PYE8Gm9bY63Oh2fTUpX11JHr807XsRSns+UhZAkQKBgQDfAdPpUcWL1cXk6cxAlIOkevzSQZhJ40+O85XGVu8OPiUgH+xvUMoU04Omny2PMB+SHUZ7MSSPaZ/4VxFHRltdA4oTWqY9hKlK38cZQmi4oQZNEp+myaztCCVPAc/ehIOVPP9EUzYZxAO8yThDp9AVCaLUt5fAY5R8TuskKW+I5QKBgQCriQgRFtDJz8UlGTdaWSWJAtIzYqK5ebK5LrG2A92GZYKhjed5cEBok9pH4TB2txJtVW9lZYtmwNbDqY9ZROX13cUQSN/m/+9Ztp27dmcZu4XmDcAeW2b6s4kLcsgxs36K16Q6kD0JCsSvGSKcXleLzlRuu3mTzGpyu6ZOfTUhxQKBgCWR6EKZjR9mz2UmYkvK+XafZbKCbIaeLgwWS3hTswiCPbzww+NpB/KEQ9yxWnqSD++M0Q/2EoO6tsGe4YFRgBjmfL+P+QvKKr87fg5qWrwoHKilZ5IwzdpNEgQCeSOyzXw5/4NiRW4H0N1gRQNY5Tx2GCGgIIe//wcGQKUh+fFlAoGAbkqLT0GWOUxH/BR8jLDo+3E+6h8vdYYULhEh3mAZ3UIDfhCZlnFgWF1IahMWcB5B0N6v40gpG4Y+EExDiShNm4peHJxeqk5A/TP3swLsQ5VZAJQyxNIMIoGxShUhNyOr52XDiIwZUmkEEXld0aCvNYsSqqgMAcXbY63Xvnuu3+kCgYB2x7xOrZ87P/XXwEki0Z2K0hAWHcrXD6OgVJ3OpTVhnM+fe2LehaBmrass8ParTRnrr7r6l+ILCYsG4tvQg4LT3VK4ROwiKuS9Xgk3YbJ1nD6RRZ+Piv0Z4gFavV3U2sY/+DVCo8WRQN6Xsh5vIGd0qaQYOb8/1BLJPRWFBZCHeQ==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlW2XjjMaHavjRnW+oZ1/amaHCQswmN13EKZU1tRJ5W10oyUY9QkZxVPMh8NCXjPb2zqBtOiZ/Iordz+FqL9JMIR33O1PdP4D6HudEqnCGLZ0DCIiNRRJIOy7IdV1SPro3c2nQLPM0JZLBBrY+Mrnl0vBPw0L4j03UC5HSL69rBv9p9ADG0iMwPrrx4IHd4sqaE5BEWxg7g8lq1AgJ8eTpll3b6tiWKxrlaPSTBF5Jh8TvIWD+BkRPSsKYxGreJJdsEn8xByS/3iHVX5FNw2tC7sKyNhfmPMMhQ5bI62ITxbTN/KFy2tu53rInKtmWMOJzK5fhX56PJUXydnga+LdOQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url ="http://127.0.0.1:8082/pay/fallback";

	//页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8082/pay/paysuccess";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("D:\\alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

