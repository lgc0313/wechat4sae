package org.gcli.weather;

import java.util.Map;

import net.sf.json.JSONObject;

import org.gcli.url.StaticPage;

public class GetJson {

	@SuppressWarnings("unchecked")
	public static String getJson(String city) {
		// url = "http://www.weather.com.cn/data/sk/"+ url + ".html";
		String url = "http://api.map.baidu.com/telematics/v3/weather?location="
				+ city + "&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
		String json = StaticPage.getStaticPage(url);
		return json;

	}

}
