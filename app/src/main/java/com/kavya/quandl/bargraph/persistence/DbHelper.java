package com.kavya.quandl.bargraph.persistence;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kavya.quandl.bargraph.data.DataSet;

/**
 * Created by gkavya on 12/14/17.
 */

public class DbHelper extends OrmLiteSqliteOpenHelper {

	private static final String DB_NAME = "dataset.db";
	private static final int DB_VERSION = 1;
	private Dao<DataSet, Integer> dataSetDao;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, DataSet.class);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

		try {
			TableUtils.dropTable(connectionSource, DataSet.class, true);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		onCreate(database, connectionSource);
	}

	public Dao<DataSet, Integer> getInformationDao() throws SQLException, java.sql.SQLException {
		if (dataSetDao == null) {
			dataSetDao = getDao(DataSet.class);
		}
		return dataSetDao;
	}
}
