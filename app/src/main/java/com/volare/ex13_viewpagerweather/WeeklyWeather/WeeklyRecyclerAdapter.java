package com.volare.ex13_viewpagerweather.WeeklyWeather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volare.ex13_viewpagerweather.R;

import java.util.ArrayList;

public class WeeklyRecyclerAdapter extends RecyclerView.Adapter< WeeklyRecyclerAdapter.Holder > {

	private Context context;
	private ArrayList< WeeklyModel > weeklyW;

	public WeeklyRecyclerAdapter ( Context context , ArrayList< WeeklyModel > weeklyW ) {

		this.context = context;
		this.weeklyW = weeklyW;
	}

	@NonNull
	@Override
	public Holder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

		View view = LayoutInflater.from( context ).inflate( R.layout.recycler_item , parent , false );

		Holder holder = new Holder( view );

		return holder;
	}

	@Override
	public void onBindViewHolder ( @NonNull Holder holder , int position ) {

		WeeklyModel item = weeklyW.get( position );

		Picasso.get().load( item.getIcon() ).resize( 200, 200 ).into( holder.ivIcon );

		holder.tvDate.setText( item.getDate() );
		holder.tvSummary.setText( item.getSummary() );
		holder.tvDetail.setText( item.getDetail() );
		holder.tvTempMin.setText( item.getTemp_Min() );
		holder.tvTempMax.setText( item.getTemp_Max() );
		holder.tvTemp.setText( item.getTemp() );

	}

	@Override
	public int getItemCount () {

		return weeklyW.size();
	}

	public class Holder extends RecyclerView.ViewHolder {

		private ImageView ivIcon;
		private TextView tvDate;
		private TextView tvTemp;
		private TextView tvTempMax;
		private TextView tvTempMin;
		private TextView tvDetail;
		private TextView tvSummary;

		public Holder ( View view ) {

			super( view );

			ivIcon = view.findViewById( R.id.iv_icon );
			tvDate = view.findViewById( R.id.tv_Date );
			tvTemp = view.findViewById( R.id.tv_Temp );
			tvTempMax = view.findViewById( R.id.tv_Temp_Max );
			tvTempMin = view.findViewById( R.id.tv_Temp_Min );
			tvDetail = view.findViewById( R.id.tv_Detail );
			tvSummary = view.findViewById( R.id.tv_Summary );
		}
	}
}

