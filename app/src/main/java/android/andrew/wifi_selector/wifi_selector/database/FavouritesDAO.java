package android.andrew.wifi_selector.wifi_selector.database;

import java.util.List;

public interface FavouritesDAO {

	void addFavourite( FavouriteWifiEntity entityToAdd );

	void removeFavourite( FavouriteWifiEntity entity );

	void removeFavourite( String ssid );

	List<FavouriteWifiEntity> getFavourites();
}
