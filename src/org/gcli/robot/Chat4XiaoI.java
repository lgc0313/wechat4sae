package org.gcli.robot;

import java.io.IOException;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

public class Chat4XiaoI {
	// 用户申请的key

	private final static String APP_KEY = "xSNLvviu1vkY";

	// 用户申请的secret

	private final static String APP_SECRET = "gTwHh9D7W63NigSlRGmb";

	/**
	 * 
	 * 调用小i机器人
	 * 
	 *
	 * 
	 * @paramkey 对应微信的content，也就是用户发送的内容
	 * 
	 * @paramnames 小i用户名
	 * 
	 * @return返回回答结果
	 */

	public static String Tess(String key, String names) {

		String realm = "xiaoi.com";
		String method = "POST";
		String uri = "/robot/ask.do";
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		String nonce = new String(Hex.encodeHex(b));
		String HA1 = DigestUtils.shaHex(StringUtils.join(new String[] {
				APP_KEY, realm, APP_SECRET }, ":"));
		String HA2 = DigestUtils.shaHex(StringUtils.join(new String[] { method,
				uri }, ":"));
		String sign = DigestUtils.shaHex(StringUtils.join(new String[] { HA1,
				nonce, HA2 }, ":"));

		String str = null;

		HttpClient hc = new HttpClient();

		PostMethod pm = new PostMethod("http://nlp.xiaoi.com/robot/ask.do");

		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");

		// key换成自己用户key

		pm.addRequestHeader("X-Auth", "app_key=\"xSNLvviu1vkY\",nonce=\"" + nonce
				+ "\", signature=\"" + sign + "\"");

		pm.setParameter("platform", "custom");

		pm.setParameter("type", "0");

		pm.setParameter("userId", names);

		pm.setParameter("question", key);

		int re_code;

		try {

			re_code = hc.executeMethod(pm);

			if (re_code == 200) {

				str = pm.getResponseBodyAsString();

			}

		} catch (HttpException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return str;

	}
}
