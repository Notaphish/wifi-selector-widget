package com.android.andrew.wifi_selector;

import android.net.wifi.WifiConfiguration;

public class WifiConfigurationDecorator {

    private final WifiConfiguration delegate;

    public WifiConfigurationDecorator(WifiConfiguration delegate) {
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
