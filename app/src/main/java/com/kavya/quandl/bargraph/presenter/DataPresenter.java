package com.kavya.quandl.bargraph.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.kavya.quandl.bargraph.BuildConfig;
import com.kavya.quandl.bargraph.MainActivity;
import com.kavya.quandl.bargraph.data.DataSet;
import com.kavya.quandl.bargraph.data.ResponseDataSet;
import com.kavya.quandl.bargraph.network.ApiManager;
import com.kavya.quandl.bargraph.persistence.DbHelper;
import com.kavya.quandl.bargraph.utils.AppUtils;


import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gkavya on 12/12/17.
 */

public class DataPresenter {

	private ApiManager manager;
	private MainActivity mMainActivity;
	private DataSet mDataSet;
	private DbHelper databaseHelper = null;
	private Dao<DataSet, Integer> dataSetDao;
	private List<DataSet> dataSetList;


	public DataPresenter(MainActivity activity, ApiManager apiManager) {
		this.mMainActivity = activity;
		this.manager = apiManager;
	}

	public void loadDataSet() {
		if (AppUtils.isNetworkConnected(mMainActivity)) {

			manager.getiResultsData().getDataSet(BuildConfig.API_KEY)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.unsubscribeOn(Schedulers.io())
					.subscribe(new Observer<ResponseDataSet>() {
						@Override
						public void onError(Throwable e) {
							e.printStackTrace();
						}

						@Override
						public void onComplete() {
							mMainActivity.updateView(mDataSet);
							fetchData();
							Toast.makeText(mMainActivity, "Completed", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSubscribe(Disposable d) {

						}

						@Override
						public void onNext(ResponseDataSet movieResultListModel) {
							mDataSet = movieResultListModel.getDatasetData();
							try {
								dataSetDao = getHelper().getInformationDao();
								dataSetDao.create(mDataSet);
							} catch (java.sql.SQLException e) {
								e.printStackTrace();
							}
						}
					});
		} else {
			Toast.makeText(mMainActivity, "NOT CONNECTED TO INTERNET", Toast.LENGTH_LONG).show();
		}

	}

	private DbHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(mMainActivity, DbHelper.class);
		}
		return databaseHelper;
	}


	protected void clear() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private void fetchData() {
		try {
			dataSetDao = getHelper().getInformationDao();
			dataSetList = dataSetDao.queryForAll();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

}
