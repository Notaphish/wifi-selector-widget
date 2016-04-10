package com.android.andrew.wifi_selector;

import android.net.wifi.WifiConfiguration;
import android.os.Parcel;
import android.os.Parcelable;

public class WifiConfigurationDecorator implements Parcelable{

    private final String ssid;

    private final WifiConfiguration delegate;

    public WifiConfigurationDecorator(WifiConfiguration wifiConfiguration) {
        delegate = wifiConfiguration;
        ssid = delegate.SSID;
    }

    public WifiConfiguration getWifiConfig() {
        return delegate;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<WifiConfigurationDecorator> CREATOR
            = new Parcelable.Creator<WifiConfigurationDecorator>() {
        public WifiConfigurationDecorator createFromParcel(Parcel in) {
            return new WifiConfigurationDecorator((WifiConfiguration) in.readParcelable(WifiConfiguration.class.getClassLoader()));
        }

        public WifiConfigurationDecorator[] newArray(int size) {
            return new WifiConfigurationDecorator[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable( delegate, flags);
    }

    @Override
    public String toString() {
        return ssid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WifiConfigurationDecorator that = (WifiConfigurationDecorator) o;

        return !(ssid != null ? !ssid.equals(that.ssid) : that.ssid != null);
    }

    @Override
    public int hashCode() {
        return ssid != null ? ssid.hashCode() : 0;
    }
}
