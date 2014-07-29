package org.gcli.weather;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WeatherSevice {

	public static String getWeather(String city) {
		String jsonStr = GetJson.getJson(city);

		if (jsonStr == null) {
			return null;
		}else {
			String strObj = new String(jsonStr);
			try {
				JSONObject jsonObject = JSONObject.fromObject(strObj);
				String status = jsonObject.getString("status");
				if(status!="success"){
					return null;
				}
				JSONObject root = jsonObject.getJSONObject("lresuts");
				String currentCity = root.getString("currentCity");
				String pm25 = root.getString("pm25");
				JSONObject weather_data = root.getJSONObject("weather_data");
				JSONArray jsonArr = JSONArray.fromObject(weather_data);
	             String date[] = new String[jsonArr.size()];
	             String weather[] = new String[jsonArr.size()];
	             String wind[] = new String[jsonArr.size()];
	             String temperature[] = new String[jsonArr.size()];
	             for (int i = 0; i < jsonArr.size(); i++) {
	            	 date[i] = jsonArr.getJSONObject(i).getString("date");
	                 weather[i] = jsonArr.getJSONObject(i).getString("weather");
	                 wind[i] = jsonArr.getJSONObject(i).getString("wind");
	                 temperature[i] = jsonArr.getJSONObject(i).getString("temperature");
	            }
	             JSONObject index = root.getJSONObject("index");
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

			} catch (JSONException e) {
				e.printStackTrace();

			}
			return null;
		}
	}

}
