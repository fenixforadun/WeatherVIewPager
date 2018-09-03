package com.volare.ex13_viewpagerweather.WeeklyWeather;

public class WeeklyModel {

	private String icon;
	private String temp;
	private String temp_Min;
	private String temp_Max;
	private String date;
	private String detail;
	private String summary;

	public WeeklyModel ( String icon , String temp , String temp_Min , String temp_Max , String date , String detail , String summary ) {

		this.icon = icon;
		this.temp = temp;
		this.temp_Min = temp_Min;
		this.temp_Max = temp_Max;
		this.date = date;
		this.detail = detail;
		this.summary = summary;
	}

	public String getIcon () {

		return icon;
	}

	public String getTemp () {

		return temp;
	}

	public String getTemp_Min () {

		return temp_Min;
	}

	public String getTemp_Max () {

		return temp_Max;
	}

	public String getDate () {

		return date;
	}

	public String getDetail () {

		return detail;
	}

	public String getSummary () {

		return summary;
	}
}
