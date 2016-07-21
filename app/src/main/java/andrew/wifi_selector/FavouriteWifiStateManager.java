package andrew.wifi_selector;

import andrew.wifi_selector.database.FavouritesDAO;
import andrew.wifi_selector.database.FavouriteWifiEntity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FavouriteWifiStateManager {

    private final LinkedHashSet<WifiConfigurationDecorator> favourites;
    private final LinkedHashSet<WifiConfigurationDecorator> knownNetworks;
    private final FavouritesDAO favouritesDAO;

    public FavouriteWifiStateManager( FavouritesDAO favouriteDao, List<WifiConfigurationDecorator> knownNetworks ) {
        this.favourites = new LinkedHashSet<>();
        this.knownNetworks = new LinkedHashSet<>(knownNetworks);
        favouritesDAO = favouriteDao;

    }

    private static FavouriteWifiEntity TRANSFORM_TO_ENTITY( WifiConfigurationDecorator source ){
        return new FavouriteWifiEntity( source.getSsid() );
    }

    public void addFavourite( WifiConfigurationDecorator wifiConfig ) {
		boolean wasAdded = favourites.add( wifiConfig );
		if ( wasAdded )
			favouritesDAO.addFavourite( TRANSFORM_TO_ENTITY( wifiConfig ) );
	}

    public void reloadFavouritesFromList( List<WifiConfigurationDecorator> incFavourites ) {
        if ( incFavourites != null )
            favourites.addAll( incFavourites );
    }

	public void reloadFavouritesFromStorage() {
		List<FavouriteWifiEntity> storedFavourites = favouritesDAO.getFavourites();
		for ( WifiConfigurationDecorator current : knownNetworks ) {
			FavouriteWifiEntity targetEntity = new FavouriteWifiEntity( current.getSsid() );
			if ( storedFavourites.contains( targetEntity )){
				favourites.add( current );
				if ( hasLoadedAllFavouritesFromStorage( storedFavourites ) )
					break;
			}
		}
	}

	private boolean hasLoadedAllFavouritesFromStorage( List<FavouriteWifiEntity> storedFavourites ) {
		return favourites.size() == storedFavourites.size();
	}


	public Set<WifiConfigurationDecorator> getKnownNetworks() {
        return knownNetworks;
    }

    public boolean removeFavourite(WifiConfigurationDecorator favourite) {
		favouritesDAO.removeFavourite( TRANSFORM_TO_ENTITY( favourite ) );
        return favourites.remove(favourite);
    }

    public Set<WifiConfigurationDecorator> getFavourites() {
        return favourites;
    }

}
