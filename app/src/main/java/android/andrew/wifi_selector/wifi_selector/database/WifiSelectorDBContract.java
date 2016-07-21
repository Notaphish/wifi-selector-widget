package android.andrew.wifi_selector.wifi_selector.database;

import android.provider.BaseColumns;

public final class WifiSelectorDBContract {

	public WifiSelectorDBContract() {
	}

	/* Inner class that defines the table contents */
	public static abstract class FeedEntry implements BaseColumns {
		public static final String TABLE_NAME = "favourte_wifi";
		public static final String COLUMN_NAME_SSID = "ssid";
	}
}
