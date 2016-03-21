package com.android.andrew.wifi_selector;

import android.net.wifi.WifiConfiguration;

public class WifiConfigurationWrapper  {

    private final WifiConfiguration delegate;

    public WifiConfigurationWrapper(WifiConfiguration delegate) {
        this.delegate = delegate;
    }

    public WifiConfiguration getWifiConfig() {
        return delegate;
    }

    @Override
    public String toString() {
        return delegate.SSID;
    }
}
