package andrew.wifi_selector.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WifiSelectDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "WIFI_SELECTOR";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_FAVOURITES_TABLE = "CREATE TABLE " + WifiSelectorDBContract.FeedEntry.TABLE_NAME + " (" + WifiSelectorDBContract.FeedEntry._ID + " INTEGER PRIMARY KEY, " + WifiSelectorDBContract.FeedEntry.COLUMN_NAME_SSID + " TEXT )";
	private static final String DROP_FAVOURITES_TABLE = "DROP TABLE IF EXISTS " + WifiSelectorDBContract.FeedEntry.TABLE_NAME;

	public WifiSelectDBHelper( Context context ) {
		super( context, DATABASE_NAME, null, DATABASE_VERSION );
	}

	@Override
	public void onCreate( SQLiteDatabase db ) {
		db.execSQL( CREATE_FAVOURITES_TABLE );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
		onCreate( db );
	}
}
