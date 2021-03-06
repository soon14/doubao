package com.xinspace.csevent.alipay;

import com.xinspace.csevent.app.utils.TimeHelper;
import com.xinspace.csevent.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInfoUtil2_0 {

	/**
	 * 构造授权参数列表
	 *
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值
		keyValues.put("auth_type", "AUTHACCOUNT");

		// 签名类型
		keyValues.put("sign_type", "RSA");

		return keyValues;
	}

	/**
	 * 构造支付订单参数列表
	 * @param app_id
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap(String app_id , String subject, String tradeNo , String price ,
														 String body ,String version) {
		Map<String, String> keyValues = new HashMap<String, String>();

		String SELLER = "coresun@coresun.net";

		keyValues.put("app_id", app_id);

		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + price + "\""
				+ ",\"subject\":" + "\""+subject + "\"" +",\"seller_id\":" + "\""+SELLER + "\"" +",\"body\":" +"\""+ body + "\",\"out_trade_no\":\"" + tradeNo  + "\"}");

		LogUtil.i("单号内容" + "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + price + "\""
				+ ",\"subject\":" + "\""+subject + "\"" +",\"seller_id\":" + "\""+SELLER + "\""+",\"body\":" +"\""+ body + "\",\"out_trade_no\":\"" + tradeNo  + "\"}");

		keyValues.put("charset", "utf-8");

		//keyValues.put("format" , "json");

		keyValues.put("method", "alipay.trade.app.pay");

		//keyValues.put("method", "mobile.securitypay.pay");

		keyValues.put("notify_url", "http://redis.coresun.net/shide/alipay/notify_url/integral_notify");
		//keyValues.put("notify_url", "http://redis.coresun.net/shide_test/alipay/notify_url/integral_notify");

		keyValues.put("sign_type", "RSA");

		keyValues.put("timestamp", TimeHelper.getDateString(String.valueOf(System.currentTimeMillis())));

		keyValues.put("version", "2.0");

		return keyValues;
	}




	/**
	 * 构造支付订单参数信息
	 *
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();

//		sb.append(buildKeyValue("app_id", "2016090601854604", false));
//		sb.append("&");

		for (int i = 0; i < keys.size() -1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, false));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, false));

		LogUtil.i("第一次构建信息" + sb.toString());

		return sb.toString();
	}

	/**
	 * 拼接键值对
	 *
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * 对支付参数信息进行签名
	 *
	 * @param map
	 *            待签名授权信息
	 *
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);
		StringBuilder authInfo = new StringBuilder();

		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		LogUtil.i("authInfo  签名的" + authInfo);

		LogUtil.i("rsaKey" + rsaKey);
		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);

		LogUtil.i("oriSign   " + oriSign);

		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		LogUtil.i("encodedSign   " + encodedSign);

		return "sign=" + encodedSign;
	}

}
