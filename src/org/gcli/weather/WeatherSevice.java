package org.gcli.weather;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WeatherSevice {

	public static StringBuffer getWeather(String city) {
		String jsonStr = GetJson.getJson(city);
		StringBuffer str = new StringBuffer("天气查询失败。");

		if (jsonStr == null) {
			return str;
		} else {
			String strObj = new String(jsonStr);
			try {
				JSONObject jsonObject = JSONObject.fromObject(strObj);
				String status = jsonObject.getString("status");
				if (!"success".equals(status)) {
					return str;
				}
				String date = jsonObject.getString("date");
				String results = jsonObject.getString("results");
				JSONArray jsonResultArr = JSONArray.fromObject(results);
				JSONObject jsonResult = JSONObject.fromObject(jsonResultArr
						.get(0));
				String currentCity = jsonResult.getString("currentCity");
				String pm25 = jsonResult.getString("pm25");
				String weather_data = jsonResult.getString("weather_data");
				JSONArray jsonArr = JSONArray.fromObject(weather_data);
				String week[] = new String[jsonArr.size()];
				String weather[] = new String[jsonArr.size()];
				String wind[] = new String[jsonArr.size()];
				String temperature[] = new String[jsonArr.size()];
				for (int i = 0; i < jsonArr.size(); i++) {
					week[i] = jsonArr.getJSONObject(i).getString("date");
					weather[i] = jsonArr.getJSONObject(i).getString("weather");
					wind[i] = jsonArr.getJSONObject(i).getString("wind");
					temperature[i] = jsonArr.getJSONObject(i).getString(
							"temperature");
				}
				String index = jsonResult.getString("index");
				jsonArr = JSONArray.fromObject(index);
				String title[] = new String[jsonArr.size()];
				String zs[] = new String[jsonArr.size()];
				String tipt[] = new String[jsonArr.size()];
				String des[] = new String[jsonArr.size()];
				for (int i = 0; i < jsonArr.size(); i++) {
					title[i] = jsonArr.getJSONObject(i).getString("title");
					zs[i] = jsonArr.getJSONObject(i).getString("zs");
					tipt[i] = jsonArr.getJSONObject(i).getString("tipt");
					des[i] = jsonArr.getJSONObject(i).getString("des");
				}
				str = new StringBuffer();
				str.append(currentCity + "天气预报").append("\n");
				for (int i = 0; i < 4; i++) {
					str.append(week[i]+" "+temperature[i]+" "+weather[i]+" "+wind[i]).append("\n");
				}

			} catch (JSONException e) {
				e.printStackTrace();

			}
			return str;
		}
	}

}
