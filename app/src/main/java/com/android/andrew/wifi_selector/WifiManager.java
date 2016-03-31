package com.android.andrew.wifi_selector;

import java.util.ArrayList;
import java.util.List;

public class WifiManager {

    private List<WifiConfigurationDecorator> favourites;
    private final List<WifiConfigurationDecorator> others;

    public WifiManager(List<WifiConfigurationDecorator> others) {
        this.favourites = new ArrayList<>();
        this.others = others;
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

    public void addNetwork( WifiConfigurationDecorator decorator ){
        others.add(decorator);
    }

    public List<WifiConfigurationDecorator> getNormalNetworks(){
        return others;
    }
}
