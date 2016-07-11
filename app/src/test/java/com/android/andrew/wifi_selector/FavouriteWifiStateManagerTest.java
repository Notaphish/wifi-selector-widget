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
import static org.mockito.Mockito.when;

public class FavouriteWifiStateManagerTest {

	public static final String SSID = "test";
	private FavouriteWifiStateManager manager;
	private List<WifiConfigurationDecorator> knownNetworks;
	private FavouritesDAO favouriteDao;

	private WifiConfigurationDecorator favourite1;
	private WifiConfigurationDecorator favourite2;

	@Before
	public void setup() {
		knownNetworks = new ArrayList<>();
		favouriteDao = mock( FavouritesDAO.class );
		manager = new FavouriteWifiStateManager( favouriteDao, knownNetworks );

		WifiConfiguration config = new WifiConfiguration();
		config.SSID = SSID;

		favourite1 = new WifiConfigurationDecorator( config );

		config.SSID = SSID + "2";
		favourite2 = new WifiConfigurationDecorator( config );
	}

	@Test
	public void addFavourite_daoCallAndAddedToFavourites() {
		manager.addFavourite( favourite1 );

		verify( favouriteDao ).addFavourite( new FavouriteWifiEntity( SSID ) );
		assertThat( manager.getFavourites(), contains( favourite1 ) );
	}

	@Test
	public void addFavourite_duplicateFavouriteNoDaoCall() {
		manager.addFavourite( favourite1 );
		verify( favouriteDao ).addFavourite( new FavouriteWifiEntity( SSID ) );

		manager.addFavourite( favourite1 );

		verifyNoMoreInteractions( favouriteDao );
		assertThat( manager.getFavourites(), contains( favourite1 ) );
	}

	@Test
	public void removeFavourite() {
		manager.addFavourite( favourite1 );

		manager.removeFavourite( favourite1 );

		verify( favouriteDao ).removeFavourite( new FavouriteWifiEntity( favourite1.getSsid() ) );
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

	@Test
	public void reloadFavouritesFromStorage_emptyStorage() {
		when( favouriteDao.getFavourites() ).thenReturn( Collections.<FavouriteWifiEntity>emptyList() );

		manager.reloadFavouritesFromStorage();

		assertThat( manager.getFavourites(), is( empty() ) );
	}

	@Test
	public void reloadFavouritesFromStorage_existingStorageAllNetworksKnown() {
		knownNetworks.add( favourite1 );
		knownNetworks.add( favourite2 );
		manager = new FavouriteWifiStateManager( favouriteDao, knownNetworks );
		when( favouriteDao.getFavourites() ).thenReturn( Lists.newArrayList( new FavouriteWifiEntity( favourite1.getSsid() ), new FavouriteWifiEntity( favourite2.getSsid() ) ) );

		manager.reloadFavouritesFromStorage();

		assertThat( manager.getFavourites(), contains( favourite1, favourite2 ) );
	}

	@Test
	public void reloadFavouritesFromStorage_onlyOneKnownNetwork() {
		knownNetworks.add( favourite2 );
		manager = new FavouriteWifiStateManager( favouriteDao, knownNetworks );
		when( favouriteDao.getFavourites() ).thenReturn( Lists.newArrayList( new FavouriteWifiEntity( favourite1.getSsid() ), new FavouriteWifiEntity( favourite2.getSsid() ) ) );

		manager.reloadFavouritesFromStorage();

		assertThat( manager.getFavourites(), contains( favourite2 ) );
	}

	@Test
	public void reloadFavouritesFromStorage_noKnownNetworks() {
		when( favouriteDao.getFavourites() ).thenReturn( Lists.newArrayList( new FavouriteWifiEntity( favourite1.getSsid() ), new FavouriteWifiEntity( favourite2.getSsid() ) ) );

		manager.reloadFavouritesFromStorage();

		assertThat( manager.getFavourites(), is( empty() ) );
	}
}
