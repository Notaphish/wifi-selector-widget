package android.andrew.wifi_selector.wifi_selector.database;

/**
 * DAO Implementation agnostic entity
 */
public class FavouriteWifiEntity {

	private String ssid;

	public FavouriteWifiEntity(String ssid ) {
		this.ssid = ssid;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid( String ssid ) {
		this.ssid = ssid;
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( o == null || getClass() != o.getClass() ) return false;

		FavouriteWifiEntity that = ( FavouriteWifiEntity ) o;

		return !(ssid != null ? !ssid.equals( that.ssid ) : that.ssid != null);

	}

	@Override
	public int hashCode() {
		return ssid != null ? ssid.hashCode() : 0;
	}
}
