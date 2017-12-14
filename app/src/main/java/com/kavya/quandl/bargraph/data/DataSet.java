package com.kavya.quandl.bargraph.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by gkavya on 12/12/17.
 */

@DatabaseTable
public class DataSet implements Parcelable {

	@DatabaseField(generatedId = true, columnName = "id")
	public int id;

	@SerializedName("limit")
	@Expose
	private Object limit;
	@SerializedName("transform")
	@Expose
	private Object transform;
	@SerializedName("column_index")
	@Expose
	private Object columnIndex;
	@SerializedName("column_names")
	@Expose
	private List<String> columnNames = null;
	@SerializedName("start_date")
	@Expose
	private String startDate;
	@SerializedName("end_date")
	@Expose
	private String endDate;
	@SerializedName("frequency")
	@Expose
	private String frequency;

	@DatabaseField(columnName = "data")
	@SerializedName("data")
	@Expose
	private List<List<String>> data = null;
	@SerializedName("collapse")
	@Expose
	private Object collapse;
	@SerializedName("order")
	@Expose
	private Object order;
	public final static Parcelable.Creator<DataSet> CREATOR = new Parcelable.Creator<DataSet>() {


		@SuppressWarnings({
				"unchecked"
		})
		public DataSet createFromParcel(Parcel in) {
			return new DataSet(in);
		}

		public DataSet[] newArray(int size) {
			return (new DataSet[size]);
		}

	};

	protected DataSet(Parcel in) {
		this.limit = ((Object) in.readValue((Object.class.getClassLoader())));
		this.transform = ((Object) in.readValue((Object.class.getClassLoader())));
		this.columnIndex = ((Object) in.readValue((Object.class.getClassLoader())));
		in.readList(this.columnNames, (java.lang.String.class.getClassLoader()));
		this.startDate = ((String) in.readValue((String.class.getClassLoader())));
		this.endDate = ((String) in.readValue((String.class.getClassLoader())));
		this.frequency = ((String) in.readValue((String.class.getClassLoader())));
		in.readList(this.data, (java.util.List.class.getClassLoader()));
		this.collapse = ((Object) in.readValue((Object.class.getClassLoader())));
		this.order = ((Object) in.readValue((Object.class.getClassLoader())));
	}

	public DataSet() {
	}

	public Object getLimit() {
		return limit;
	}

	public void setLimit(Object limit) {
		this.limit = limit;
	}

	public Object getTransform() {
		return transform;
	}

	public void setTransform(Object transform) {
		this.transform = transform;
	}

	public Object getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Object columnIndex) {
		this.columnIndex = columnIndex;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public List<List<String>> getData() {
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}

	public Object getCollapse() {
		return collapse;
	}

	public void setCollapse(Object collapse) {
		this.collapse = collapse;
	}

	public Object getOrder() {
		return order;
	}

	public void setOrder(Object order) {
		this.order = order;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(limit);
		dest.writeValue(transform);
		dest.writeValue(columnIndex);
		dest.writeList(columnNames);
		dest.writeValue(startDate);
		dest.writeValue(endDate);
		dest.writeValue(frequency);
		dest.writeList(data);
		dest.writeValue(collapse);
		dest.writeValue(order);
	}

	public int describeContents() {
		return 0;
	}
}
