package com.volare.ex13_viewpagerweather.OpenWeatherAPI;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPI {

	static final String API_URL = "https://api.openweathermap.org/data/2.5/";
	static final String API_ID = "502a9de873901a5ba4248a44b18aaa1b";
	static final String UNIT = "metric";

	static final String ICON_BASE_URL = "http://motive.co.kr/images/weather/";

	@GET("forecast")
	Call< JsonObject > getWeeklyWeather (

					@Query("addid") String appid ,
					@Query("lat") double lat ,
					@Query("lon") double lon ,
					@Query("units") String units

	);

	@GET("weather")
	Call< JsonObject > getDailyWeather (

					@Query("addid") String appid ,
					@Query("lat") double lat ,
					@Query("lon") double lon ,
					@Query("units") String units
	);


}

