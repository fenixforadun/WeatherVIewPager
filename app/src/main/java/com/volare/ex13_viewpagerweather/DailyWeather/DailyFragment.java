package com.volare.ex13_viewpagerweather.DailyWeather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.volare.ex13_viewpagerweather.DailyWeather.DailyData.DailyWeather;
import com.volare.ex13_viewpagerweather.OpenWeatherAPI.OpenWeatherAPI;
import com.volare.ex13_viewpagerweather.OpenWeatherAPI.SetRetrofit2;
import com.volare.ex13_viewpagerweather.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment implements Callback< JsonObject > {

	private double lat;
	private double lon;

	private ImageView ivIcon;
	private TextView tvLat;
	private TextView tvLon;
	private TextView tvCity;
	private TextView tvDetail;
	private TextView tvTemp;
	private TextView tvHumid;
	private TextView tvWind;
	private TextView tvRain;

	private View view;

	private Gson gson = new Gson();
	private Call< JsonObject > call;
	private DailyWeather dailyW;

	public DailyFragment () {
		// Required empty public constructor
	}


	@Override // 이부분이 먼저 실행되므로 컨스트럭터를 사용할 수 없다.
	public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
														 Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		View view = inflater.inflate( R.layout.fragment_daily , container , false );

		initView();
		initData();

		return view;
	}

	public void initView () { // 전역변수로 view를 선언했으므로 initView가 view를 참조할 필요 없음

		lat = getArguments().getDouble( "lat" );
		lon = getArguments().getDouble( "lon" ); // 번들로 보내진 데이터를 받는 메소드

		tvLat = view.findViewById( R.id.latitude );
		tvLon = view.findViewById( R.id.longitude );
		ivIcon = view.findViewById( R.id.iv_icon );
		tvCity = view.findViewById( R.id.cityName );
		tvDetail = view.findViewById( R.id.weatherTitle );
		tvTemp = view.findViewById( R.id.temperature );
		tvHumid = view.findViewById( R.id.humidity );
		tvWind = view.findViewById( R.id.windSpeed );
		tvRain = view.findViewById( R.id.rain );
	}

	private void initData () {

		tvLat.setText( lat + "" );
		tvLon.setText( lon + "" );

		call = SetRetrofit2.retrofit()
						.create( OpenWeatherAPI.class ).getDailyWeather( OpenWeatherAPI.API_ID , lat , lon , OpenWeatherAPI.UNIT );
		call.enqueue( this );

	}

	@Override
	public void onResponse ( Call< JsonObject > call , Response< JsonObject > response ) {

		if ( response.isSuccessful() ) {
			dailyW = gson.fromJson( response.body() , DailyWeather.class ); // 변수가 참조하는 class를 직접 참조
			if ( dailyW != null ) {
				tvCity.setText( dailyW.getName() );

				String iconUrl = OpenWeatherAPI.ICON_BASE_URL + dailyW.getWeather().get( 0 ).getIcon() + ".png";
				Picasso.get().load( iconUrl ).resize( 100, 100 ).into( ivIcon );

				String temp = dailyW.getMain().getTemp()+"℃";
				String humid = dailyW.getMain().getHumidity()+"%";
				String windSpeed = dailyW.getWind().getSpeed()+"m/s";
				String detail = dailyW.getWeather().get( 0 ).getMain();
				detail += " / " + dailyW.getWeather().get( 0 ).getDescription();

				String cityName = dailyW.getName();

				tvCity.setText( cityName );
				tvDetail.setText( detail );
				tvTemp.setText( temp );
				tvHumid.setText( humid );
				tvWind.setText( windSpeed );

			}
		} else {
			Toast.makeText( getActivity() , "데이터 이상" , Toast.LENGTH_LONG ).show();
		}
	}

	@Override
	public void onFailure ( Call< JsonObject > call , Throwable t ) {

		Toast.makeText( getActivity() , "통신 실패" , Toast.LENGTH_LONG ).show();
		// fragment에서 Context는 getApplicationContext()가 아닌 getActivity()로 가져와야함
	}
}
