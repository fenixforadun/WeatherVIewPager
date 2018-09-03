package com.volare.ex13_viewpagerweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.volare.ex13_viewpagerweather.DailyWeather.DailyFragment;
import com.volare.ex13_viewpagerweather.OpenWeatherAPI.PermitGPS;
import com.volare.ex13_viewpagerweather.WeeklyWeather.WeeklyFragment;

public class MainActivity extends AppCompatActivity implements LocationListener {

	private ViewPager viewPager;
	private MainPagerAdapter adater;
	private TabLayout tabLayout;
	private DailyFragment dailyFragment;
	private WeeklyFragment weeklyFragment;

	private LocationManager locationManager;

	private double lat = 35.6;
	private double lon = 126.3;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		initLocation();

	}

	private void initLocation () {

		locationManager = ( LocationManager ) getSystemService( Context.LOCATION_SERVICE );

		if ( locationManager != null ) {
			PermitGPS.checkGPS( this , locationManager , 1000 , 5 );
		}

	}

	private void initView () {

		tabLayout = findViewById( R.id.activity_main_tab_layout );
		viewPager = findViewById( R.id.activity_main_view_pager );

		adater = new MainPagerAdapter( getSupportFragmentManager() );
		dailyFragment = new DailyFragment();
		weeklyFragment = new WeeklyFragment();

		Bundle bundle = new Bundle();
		bundle.putDouble( "lat" , lat );
		bundle.putDouble( "lon" , lon );

		dailyFragment.setArguments( bundle );
		weeklyFragment.setArguments( bundle ); // intent와 비슷하게 인자를 전송해서 참조할 수 있게 함

		adater.AddFragment( dailyFragment , "Daily Weather" );
		adater.AddFragment( weeklyFragment , "Weekly Weather" );

		viewPager.setAdapter( adater );
		tabLayout.setupWithViewPager( viewPager );

	}

	@Override
	public void onLocationChanged ( Location location ) {

		lat = location.getLatitude();
		lon = location.getLongitude();

		initView();

	}

	@Override
	public void onStatusChanged ( String provider , int status , Bundle extras ) {

	}

	@Override
	public void onProviderEnabled ( String provider ) {

	}

	@Override
	public void onProviderDisabled ( String provider ) {

	}
}
