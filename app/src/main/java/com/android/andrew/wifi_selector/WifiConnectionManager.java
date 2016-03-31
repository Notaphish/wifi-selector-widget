package com.android.andrew.wifi_selector;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class WifiConnectionManager {

    private final WifiManager wifiManager;
    private final List<WifiConfigurationDecorator> networkIds;


    public WifiConnectionManager( Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        networkIds = new ArrayList<>();
        populateNetworks();
    }

    private void populateNetworks() {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        //No configured wifi networks
        if ( configuredNetworks == null ){
            return;
        }
        for (WifiConfiguration wifiConfig : configuredNetworks) {
            networkIds.add(new WifiConfigurationDecorator(wifiConfig));
        }
    }

    public List<WifiConfigurationDecorator> getKnownNetworks(){
        return networkIds;
    }

    public WifiConfigurationDecorator getKnownNetwork( int i){
        return networkIds.get(i);
    }

    public void connect( WifiConfigurationDecorator wifiConfig ){
        wifiManager.enableNetwork(wifiConfig.getWifiConfig().networkId, true );
    }
}
