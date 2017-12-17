package com.kavya.quandl.bargraph.presenter;

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

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
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


	public DataPresenter(MainActivity activity, ApiManager apiManager) {
		this.mMainActivity = activity;
		this.manager = apiManager;
	}

	public void loadDataSet() {

		List<DataSet> dataList = fetchData();
		if (dataList != null && dataList.size() > 0) {
			DataSet dataSetTable = dataList.get(0);
			mMainActivity.updateView(dataSetTable);
		} else if (AppUtils.isNetworkConnected(mMainActivity)) {
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
							//addToDb(mDataSet);
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


	public void clear() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private List<DataSet> fetchData() {
		try {
			dataSetDao = getHelper().getInformationDao();
			return dataSetDao.queryForAll();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void addToDb(DataSet dataSet) {
		Observable.just(dataSet)
				.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.computation())
				.subscribe(new Observer<DataSet>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(DataSet dataSet) {
						try {
							dataSetDao = getHelper().getInformationDao();
							dataSetDao.create(mDataSet);
						} catch (java.sql.SQLException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});

	}


}