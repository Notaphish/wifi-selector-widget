package android.andrew.wifi_selector.wifi_selector.database.dao;

import android.andrew.wifi_selector.wifi_selector.database.FavouriteWifiEntity;
import android.andrew.wifi_selector.wifi_selector.database.FavouritesDAO;
import android.andrew.wifi_selector.wifi_selector.database.WifiSelectDBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.andrew.wifi_selector.wifi_selector.database.WifiSelectorDBContract.FeedEntry;
import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

public class SQLiteFavouritesDAOImpl implements FavouritesDAO {

	public static final String TAG = "SQLITEDAO";
	private final WifiSelectDBHelper dbHelper;
	private final SQLiteDatabase writiableDb;
	private final SQLiteDatabase readableDb;


	public SQLiteFavouritesDAOImpl( Context context ) {
		this( new WifiSelectDBHelper( context ) );
	}

	@VisibleForTesting
	SQLiteFavouritesDAOImpl( WifiSelectDBHelper helper ) {
		dbHelper = helper;
		writiableDb = dbHelper.getWritableDatabase();
		readableDb = dbHelper.getReadableDatabase();
	}

	@Override
	public void addFavourite( FavouriteWifiEntity entityToAdd ) {
		ContentValues values = new ContentValues();
		values.put( FeedEntry.COLUMN_NAME_SSID, entityToAdd.getSsid() );
		long result = writiableDb.insert( FeedEntry.TABLE_NAME, null, values );
		if ( result == -1 ){
			//TODO should be nice exceptions
			throw new RuntimeException( "Failed to insert into database");
		}
		Log.d( TAG, "Added favourite with ssd " + entityToAdd.getSsid() + " to database" );
	}

	@Override
	public void removeFavourite( FavouriteWifiEntity entity ) {
		removeFavourite( entity.getSsid() );
	}

	@Override
	public void removeFavourite( String ssid ) {
		int deletedItems = writiableDb.delete( FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_SSID + "=?", new String[]{ssid} );
		if ( deletedItems > 1)
			Log.d( TAG, "Deleted more than one favourite - strange. Total items delete is " + deletedItems );
		else if ( deletedItems == 0 )
			Log.d( TAG, "Failed to delete the favourite with ssid " + ssid);
		else
			Log.d( TAG, "Deleted one item from the db with ssid " + ssid );

	}

	@Override
	public List<FavouriteWifiEntity> getFavourites() {
		Cursor selection = readableDb.query( false, FeedEntry.TABLE_NAME, new String[]{FeedEntry.COLUMN_NAME_SSID},null ,null, null, null, null, null );

		List<FavouriteWifiEntity> entities = new ArrayList<>();
		while ( selection.moveToNext() ) {
			entities.add( new FavouriteWifiEntity( selection.getString( 0 ) ) );
			Log.d( TAG, "Loaded an entry with ssid " + selection.getString( 0 ) + " from the database" );
		}
		selection.close();
		return entities;
	}
}
