package com.android.andrew.wifi_selector;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class WifiManager {

    private final LinkedHashSet<WifiConfigurationDecorator> favourites;
    private final LinkedHashSet<WifiConfigurationDecorator> knownNetworks;

    public WifiManager(List<WifiConfigurationDecorator> knownNetworks) {
        this.favourites = new LinkedHashSet<>();
        this.knownNetworks = new LinkedHashSet<>(knownNetworks);
    }

    public void addFavourite(WifiConfigurationDecorator wifiConfig) {
        favourites.add(wifiConfig);
    }

    public void addFavourites(List<WifiConfigurationDecorator> incFavourites) {
        favourites.addAll(incFavourites);
    }

    public Set<WifiConfigurationDecorator> getKnownNetworks() {
        return knownNetworks;
    }

    public boolean removeFavourite(WifiConfigurationDecorator favourite) {
        return favourites.remove(favourite);
    }

    public Set<WifiConfigurationDecorator> getFavourites() {
        return favourites;
    }

}
