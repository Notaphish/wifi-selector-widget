package com.android.andrew.wifi_selector;

import android.net.wifi.WifiConfiguration;

import com.android.andrew.wifi_selector.database.FavouriteWifiEntity;
import com.android.andrew.wifi_selector.database.FavouritesDAO;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class WifiManagerTest {

	public static final String SSID = "test";
	private WifiManager manager;
	private List<WifiConfigurationDecorator> knownNetworks;
	private FavouritesDAO mockDao;

	private WifiConfigurationDecorator favourite1;
	private WifiConfigurationDecorator favourite2;

	@Before
	public void setup() {
		knownNetworks = new ArrayList<>();
		mockDao = mock( FavouritesDAO.class );
		manager = new WifiManager( mockDao, knownNetworks );

		WifiConfiguration config = new WifiConfiguration();
		config.SSID = SSID;

		favourite1 = new WifiConfigurationDecorator( config );

		config.SSID = SSID + "2";
		favourite2 = new WifiConfigurationDecorator( config );
	}

	@Test
	public void addFavourite_daoCallAndAddedToFavourites() {
		manager.addFavourite( favourite1 );

		verify( mockDao ).addFavourite( new FavouriteWifiEntity( SSID ) );
		assertThat( manager.getFavourites(), contains( favourite1 ) );
	}

	@Test
	public void addFavourite_duplicateFavouriteNoDaoCall() {
		manager.addFavourite( favourite1 );
		verify( mockDao ).addFavourite( new FavouriteWifiEntity( SSID ) );

		manager.addFavourite( favourite1 );

		verifyNoMoreInteractions( mockDao );
		assertThat( manager.getFavourites(), contains( favourite1 ) );
	}

	@Test
	public void removeFavourite() {
		manager.addFavourite( favourite1 );

		manager.removeFavourite( favourite1 );

		verify( mockDao ).removeFavourite( new FavouriteWifiEntity( SSID ) );
		assertThat( manager.getFavourites(), not( contains( favourite1 ) ) );
	}

	@Test
	public void reloadFavouritesFromList_populatedList() {
		manager.reloadFavouritesFromList( Lists.newArrayList( favourite1, favourite2 ) );

		assertThat( manager.getFavourites(), contains( favourite1, favourite2 ) );
	}

	@Test
	public void reloadFavouritesFromList_emptyList() {
		manager.reloadFavouritesFromList( Collections.<WifiConfigurationDecorator>emptyList() );

		assertThat( manager.getFavourites(), is( empty() ) );
	}

	@Test
	public void reloadFavouritesFromList_null() {
		manager.reloadFavouritesFromList( null );

		assertThat( manager.getFavourites(), is( empty() ) );
	}

}
