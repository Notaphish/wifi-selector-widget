package com.android.andrew.wifi_selector;

import android.content.Context;
import android.net.wifi.WifiConfiguration;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WifiExpandableListAdapterTest {

    private WifiExpandableListAdapter adapter;
    private WifiConfigurationDecorator favourite1;
    private WifiConfigurationDecorator favourite2;
    private WifiConfigurationDecorator other2;
    private WifiConfigurationDecorator other1;

    @Before
    public void setup() {
        WifiConfiguration config1 = new WifiConfiguration();
        WifiConfiguration config2 =  new WifiConfiguration();
        WifiConfiguration config3 =  new WifiConfiguration();
        WifiConfiguration config4 =  new WifiConfiguration();
        config1.SSID="Fav1";
        config2.SSID="Fav2";
        config3.SSID="Fav3";
        config4.SSID="Fav4";
        favourite1 = new WifiConfigurationDecorator(config1);
        favourite2 = new WifiConfigurationDecorator(config2);
        other1 = new WifiConfigurationDecorator(config3);
        other2 = new WifiConfigurationDecorator(config4);

        Context mockContext = mock(Context.class);
        when(mockContext.getString(anyInt())).thenReturn("Favourites").thenReturn("Others");
        adapter = new WifiExpandableListAdapter(mockContext, Lists.newArrayList(favourite1, favourite2), Lists.newArrayList(favourite1, favourite2, other1, other2));
    }

    @Test
    public void testRemoveFavourite_happyPath(){
        WifiConfigurationDecorator removedItem = adapter.removeFromFavourites(0);
        assertThat("favourite successfully removed", removedItem, equalTo( favourite1) );
    }

    @Test
    public void testRemoveFavourite_happyPathLimit(){
        WifiConfigurationDecorator removedItem = adapter.removeFromFavourites(1);
        assertThat("correct item removed at boundary", removedItem, equalTo(favourite2));
    }

    @Test
    public void testRemoveFavourite_nonFavouriteRemoval(){
        WifiConfigurationDecorator removedItem = adapter.removeFromFavourites(3);
        assertThat("non-favourite is not removed", removedItem, is(nullValue()));
    }
}
