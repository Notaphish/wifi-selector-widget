package com.android.andrew.wifi_selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 23/03/2016.
 */
public class WifiManager {

    private List<WifiConfigurationDecorator> favourites;

    public WifiManager(){
        this.favourites = new ArrayList<>();
    }

    public WifiManager(List<WifiConfigurationDecorator> favourites) {
        this.favourites = favourites;
    }

    public void addFavourite( WifiConfigurationDecorator wifiConfig){
        favourites.add(wifiConfig);
    }

    public List<WifiConfigurationDecorator> getFavourites(){
        return favourites;
    }

    public WifiConfigurationDecorator getFavourite( int index ){
        return favourites.get(index);
    }
}
