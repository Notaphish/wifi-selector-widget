package com.android.andrew.wifi_selector;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class WifiConnectionManager {

    private final WifiManager wifiManager;
    private final List<WifiConfigurationDecorator> knownNetworks;


    public WifiConnectionManager( Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        knownNetworks = new ArrayList<>();
        populateNetworks();
    }

    private void populateNetworks() {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        //No configured wifi networks
        if ( configuredNetworks == null ){
            return;
        }
        for (WifiConfiguration wifiConfig : configuredNetworks) {
            knownNetworks.add( new WifiConfigurationDecorator( wifiConfig ) );
        }
    }

    public List<WifiConfigurationDecorator> getKnownNetworks(){
        return knownNetworks;
    }

    public void connect( WifiConfigurationDecorator wifiConfig ){
        wifiManager.enableNetwork(wifiConfig.getWifiConfig().networkId, true );
    }
}
