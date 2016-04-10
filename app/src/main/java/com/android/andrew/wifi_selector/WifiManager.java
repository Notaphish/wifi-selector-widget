package com.android.andrew.wifi_selector;

import java.util.ArrayList;
import java.util.List;

public class WifiManager {

    private ArrayList<WifiConfigurationDecorator> favourites;
    private final List<WifiConfigurationDecorator> knownNetworks;

    public WifiManager(List<WifiConfigurationDecorator> knownNetworks) {
        this.favourites = new ArrayList<>();
        this.knownNetworks = knownNetworks;
    }

    public void addFavourite( WifiConfigurationDecorator wifiConfig){
        favourites.add(wifiConfig);
    }
    public void addFavourites( List<WifiConfigurationDecorator> incFavourites ){
        favourites.addAll(incFavourites);
    }

    public ArrayList<WifiConfigurationDecorator> getFavourites(){
        return favourites;
    }

    public WifiConfigurationDecorator getFavourite( int index ){
        return favourites.get(index);
    }

    public void addNetwork( WifiConfigurationDecorator decorator ){
        knownNetworks.add(decorator);
    }

    public List<WifiConfigurationDecorator> getKnownNetworks(){
        return knownNetworks;
    }
}
