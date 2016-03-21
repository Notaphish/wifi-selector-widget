package com.android.andrew.wifi_selector;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class WifiConnectionManager {

    private final WifiManager wifiManager;

    public WifiConnectionManager( Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    List<WifiConfigurationWrapper> getKnownWifiNetworks(){
        List<WifiConfigurationWrapper> networkIds = new ArrayList<>();
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        //No configured wifi networks
        if ( configuredNetworks == null ){
            return new ArrayList<>();
        }

        for (WifiConfiguration wifiConfig : configuredNetworks) {
            networkIds.add( new WifiConfigurationWrapper( wifiConfig ) );
        }
        return networkIds;
    }

    void connect( WifiConfigurationWrapper wifiConfig ){
        wifiManager.enableNetwork(wifiConfig.getWifiConfig().networkId, true );
    }
}
