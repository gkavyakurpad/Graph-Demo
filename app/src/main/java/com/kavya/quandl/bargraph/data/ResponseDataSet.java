package com.kavya.quandl.bargraph.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gkavya on 12/12/17.
 */

public class ResponseDataSet implements Parcelable {

	@SerializedName("dataset_data")
	@Expose
	private DataSet datasetData;
	public final static Parcelable.Creator<ResponseDataSet> CREATOR = new Creator<ResponseDataSet>() {

		@SuppressWarnings({
				"unchecked"
		})
		public ResponseDataSet createFromParcel(Parcel in) {
			return new ResponseDataSet(in);
		}

		public ResponseDataSet[] newArray(int size) {
			return (new ResponseDataSet[size]);
		}
	};

	protected ResponseDataSet(Parcel in) {
		this.datasetData = ((DataSet) in.readValue((DataSet.class.getClassLoader())));
	}

	public ResponseDataSet() {
	}

	public DataSet getDatasetData() {
		return datasetData;
	}

	public void setDatasetData(DataSet datasetData) {
		this.datasetData = datasetData;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(datasetData);
	}

	public int describeContents() {
		return 0;
	}

}
