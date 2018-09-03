package com.volare.ex13_viewpagerweather.WeeklyWeather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.volare.ex13_viewpagerweather.OpenWeatherAPI.OpenWeatherAPI;
import com.volare.ex13_viewpagerweather.OpenWeatherAPI.SetRetrofit2;
import com.volare.ex13_viewpagerweather.R;
import com.volare.ex13_viewpagerweather.WeeklyWeather.WeeklyData.WeeklyWeather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyFragment extends Fragment implements Callback< JsonObject > {

	private View view;
	private RecyclerView recyclerView;
	private ArrayList< WeeklyModel > weeklyModels = new ArrayList<>();

	private Gson gson = new Gson();
	private Call< JsonObject > call;
	private WeeklyWeather weeklyW;

	private double lat;
	private double lon;

	private WeeklyRecyclerAdapter adapter;


	public WeeklyFragment () {
		// Required empty public constructor
	}


	@Override
	public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
														 Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_weekly , container , false );
		recyclerView = view.findViewById( R.id.fragment_weekly_recycler_view );

		lat = getArguments().getDouble( "lat" );
		lon = getArguments().getDouble( "lon" );

		initData();

		return view;
	}

	private void initData () {

		call = SetRetrofit2.retrofit()
						.create( OpenWeatherAPI.class ).getWeeklyWeather( OpenWeatherAPI.API_ID , lat , lon , OpenWeatherAPI.UNIT );
		call.enqueue( this ); // this는 onRespones로 향함

	}

	@Override
	public void onResponse ( Call< JsonObject > call , Response< JsonObject > response ) {

		if ( response.isSuccessful() ) {

			weeklyW = gson.fromJson( response.body() , WeeklyWeather.class );

			for ( int i = 0; i < weeklyW.getCnt(); i++ ) {
				String icon = OpenWeatherAPI.ICON_BASE_URL;
				icon += weeklyW.getList().get( i ).getWeather().get( 0 ).getIcon();
				icon += ".png";

				String dtText = weeklyW.getList().get( i ).getDtTxt();

				int time = Integer.parseInt( dtText.substring( 11 , 13 ) ) + 9;
				if ( time >= 24 ) time -= 24; // 24시 기준 시간보정 (time = time - 24;)

				String date = Integer.parseInt( dtText.substring( 5 , 7 ) ) + "월";
				date += Integer.parseInt( dtText.substring( 8 , 10 ) ) + "일";
				date += time + "시";

				String temp = weeklyW.getList().get( i ).getMain().getTemp() + "℃";
				String tempMax = weeklyW.getList().get( i ).getMain().getTempMax() + "℃";
				String tempMin = weeklyW.getList().get( i ).getMain().getTempMin() + "℃";

				String deg = Integer.parseInt( String.valueOf( Math.round( weeklyW.getList().get( i ).getWind().getDeg() ) ) ) + "°";
				String speed = weeklyW.getList().get( i ).getWind().getSpeed() + "m/s";
				String detail = deg + " / " + speed;

				String summary = weeklyW.getList().get( i ).getWeather().get( 0 ).getMain();
				summary += " / " + weeklyW.getList().get( i ).getWeather().get( 0 ).getDescription();

				weeklyModels.add( new WeeklyModel( icon , temp , tempMin , tempMax , date , detail , summary ) );

				// for 문안에 별도의 메소드가 들어가 있으면 반복되면서 메모리에 부하를 줄 수 있다. 따라서 끝난 뒤에 넣어주는것이 효율 좋음
			}

			initAdapter();

		} else {
			Toast.makeText( getActivity() , "데이터 이상" , Toast.LENGTH_LONG ).show();
		}

	}

	private void initAdapter() {
		adapter = new WeeklyRecyclerAdapter( getActivity(), weeklyModels );
		recyclerView.setAdapter( adapter );
		LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
		layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
		recyclerView.setLayoutManager( layoutManager );
	}

	@Override
	public void onFailure ( Call< JsonObject > call , Throwable t ) {

		Toast.makeText( getActivity() , "통신 실패" , Toast.LENGTH_LONG ).show();
	}
}

