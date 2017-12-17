package com.kavya.quandl.bargraph;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kavya.quandl.bargraph.data.DataSet;
import com.kavya.quandl.bargraph.network.ApiManager;
import com.kavya.quandl.bargraph.presenter.DataPresenter;
import com.kavya.quandl.bargraph.view.DrawBarChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private DataPresenter mDataSetPresenter;
	private DrawBarChart mChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dataset);
		mChart = findViewById(R.id.barGraph_content);
		ApiManager mApiManager = new ApiManager();
		mDataSetPresenter = new DataPresenter(this, mApiManager);
		mDataSetPresenter.loadDataSet();
	}

	public void updateView(DataSet dataSet) {
		if (dataSet != null) {
			List<List<String>> dataPoints = dataSet.getData();
			List<String> dataValues = dataPoints.get(0);
			List<String> graphPoints = dataValues.subList(1, dataValues.size());
			ArrayList<Float> fResult = new ArrayList<>(graphPoints.size());
			for (int i = 0; i < graphPoints.size(); i++) {
				float number = Float.parseFloat(graphPoints.get(i));
				float rounded = Math.round(number * 1000) / 1000f;
				fResult.add(rounded);
			}
			mChart.setBottomTextList(new ArrayList<String>(graphPoints));
			mChart.setGraphAttributes(Color.WHITE, Color.WHITE);
			mChart.setDataList(fResult, 100);
			mChart.invalidate();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDataSetPresenter.clear();
	}
}
