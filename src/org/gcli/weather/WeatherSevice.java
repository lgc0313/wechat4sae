package org.gcli.weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gcli.course.message.resp.Article;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WeatherSevice {

	public static List<Article> getWeather(String city) {
		String jsonStr = GetJson.getJson(city);
		StringBuffer str = new StringBuffer("天气查询失败。");
		List<Article> articleList = new ArrayList<Article>();

		if (jsonStr == null) {
			return articleList;
		} else {
			String strObj = new String(jsonStr);
			try {
				JSONObject jsonObject = JSONObject.fromObject(strObj);
				String status = jsonObject.getString("status");
				if (!"success".equals(status)) {
					return articleList;
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
				String dayPictureUrl[]= new String[jsonArr.size()];
				for (int i = 0; i < jsonArr.size(); i++) {
					week[i] = jsonArr.getJSONObject(i).getString("date");
					weather[i] = jsonArr.getJSONObject(i).getString("weather");
					wind[i] = jsonArr.getJSONObject(i).getString("wind");
					temperature[i] = jsonArr.getJSONObject(i).getString(
							"temperature");
					dayPictureUrl[i] = jsonArr.getJSONObject(i).getString(
							"dayPictureUrl");
				}
//				String index = jsonResult.getString("index");
//				jsonArr = JSONArray.fromObject(index);
//				String title[] = new String[jsonArr.size()];
//				String zs[] = new String[jsonArr.size()];
//				String tipt[] = new String[jsonArr.size()];
//				String des[] = new String[jsonArr.size()];
//				for (int i = 0; i < jsonArr.size(); i++) {
//					title[i] = jsonArr.getJSONObject(i).getString("title");
//					zs[i] = jsonArr.getJSONObject(i).getString("zs");
//					tipt[i] = jsonArr.getJSONObject(i).getString("tipt");
//					des[i] = jsonArr.getJSONObject(i).getString("des");
//				}
				str = new StringBuffer();
				String weatherinfo[] = new String[4];
				str.append(currentCity + "天气预报").append("\n");
				for (int i = 0; i < 4; i++) {
					weatherinfo[i]=(week[i]+" "+temperature[i]+" "+weather[i]+" "+wind[i]+"\n");
				} 
				Article article1 = new Article();  
                article1.setTitle(weatherinfo[0]);  
                article1.setDescription("");  
                article1.setPicUrl("");  
                article1.setUrl("");  

                Article article2 = new Article();  
                article2.setTitle(weatherinfo[1]);  
                article2.setDescription("");  
                article2.setPicUrl(dayPictureUrl[1]);  
                article2.setUrl("");  

                Article article3 = new Article();  
                article3.setTitle(weatherinfo[2]);  
                article3.setDescription("");  
                article3.setPicUrl(dayPictureUrl[3]);  
                article3.setUrl("");  
                
                Article article4 = new Article();  
                article4.setTitle(weatherinfo[3]);  
                article4.setDescription("");  
                article4.setPicUrl(dayPictureUrl[3]);  
                article4.setUrl(""); 

                articleList.add(article1);  
                articleList.add(article2);  
                articleList.add(article3);  
                articleList.add(article4); 
               

			} catch (JSONException e) {
				e.printStackTrace();

			}
			return articleList;
		}
	}

}
