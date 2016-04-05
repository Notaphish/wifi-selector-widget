package com.android.andrew.wifi_selector;

import android.net.wifi.WifiConfiguration;
import android.os.Parcel;
import android.os.Parcelable;

public class WifiConfigurationDecorator implements Parcelable{

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
}
