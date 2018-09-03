package com.volare.ex13_viewpagerweather.OpenWeatherAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetRetrofit2 {

	public static Retrofit retrofit () {

		Retrofit retrofit = new Retrofit.Builder()
						.baseUrl( OpenWeatherAPI.API_URL )
						.addConverterFactory( GsonConverterFactory.create() )
						.build();

		return retrofit;
	}

}
