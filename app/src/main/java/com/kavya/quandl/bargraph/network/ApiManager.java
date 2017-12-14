package com.kavya.quandl.bargraph.network;

import com.kavya.quandl.bargraph.constants.AppConstants;
import com.kavya.quandl.bargraph.data.ResponseDataSet;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gkavya on 12/12/17.
 */

public class ApiManager {

	private static IResultsData iResultsData;
	public static OkHttpClient httpClient = new OkHttpClient();

	public interface IResultsData {
		@GET("/api/v3/datasets/WIKI/FB/data.json")
		Observable<ResponseDataSet> getDataSet(@Query("api_key") String apiKey);
	}


	private static Retrofit.Builder builder = new Retrofit.Builder()
			.baseUrl(AppConstants.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

	public ApiManager() {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		clientBuilder.addInterceptor(loggingInterceptor);
		Retrofit retrofit = builder.client(clientBuilder.build()).build();
		iResultsData = retrofit.create(IResultsData.class);
	}

	public IResultsData getiResultsData() {
		return iResultsData;
	}

}
