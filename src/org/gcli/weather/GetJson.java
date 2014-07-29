package org.gcli.weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONObject;

import org.gcli.url.StaticPage;

public class GetJson {

	@SuppressWarnings("unchecked")
	public static String getJson(String city) {
		// url = "http://www.weather.com.cn/data/sk/"+ url + ".html";
		String json = "";
		try {
			city = URLEncoder.encode(city, "UTF-8");

			String url = "http://api.map.baidu.com/telematics/v3/weather?location="
					+ city + "&output=json&ak=640f3985a6437dad8135dae98d775a09";
			json = StaticPage.getStaticPage(url);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return json;

	}

}
