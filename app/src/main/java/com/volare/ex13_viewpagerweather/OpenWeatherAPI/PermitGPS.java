package com.volare.ex13_viewpagerweather.OpenWeatherAPI;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class PermitGPS {

	public static void checkGPS ( Activity activity , LocationManager locationManager , int min , int distance ) {

		int permitGrant = PackageManager.PERMISSION_GRANTED;
		String finePermit = Manifest.permission.ACCESS_FINE_LOCATION;
		String coarsePermit = Manifest.permission.ACCESS_COARSE_LOCATION;
		int selfFine = ActivityCompat.checkSelfPermission( activity.getApplicationContext() , finePermit );
		int selfCoarse = ActivityCompat.checkSelfPermission( activity.getApplicationContext() , coarsePermit );

		if ( selfFine != permitGrant && selfCoarse != permitGrant ) {
			ActivityCompat.requestPermissions( activity , new String[]{ finePermit , coarsePermit } , 100 );

			return;

		}

		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER , min , distance , ( LocationListener ) activity );
		locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER , min , distance , ( LocationListener ) activity );

	}

}

