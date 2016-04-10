package com.android.andrew.wifi_selector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

import com.android.andrew.wifi_selector.R;
import com.android.andrew.wifi_selector.WifiConfigurationDecorator;
import com.android.andrew.wifi_selector.WifiConnectionManager;
import com.android.andrew.wifi_selector.WifiExpandableListAdapter;
import com.android.andrew.wifi_selector.WifiManager;

public class WifiAccessActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_FAVOURITES = "FAVOURITES";
    private WifiConnectionManager wifiConnecitonManager;
    private WifiManager wifiManager;
    private WifiExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_access);
        wifiConnecitonManager = new WifiConnectionManager(getApplicationContext());
        wifiManager = new WifiManager(wifiConnecitonManager.getKnownNetworks());
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_KEY_FAVOURITES))
            wifiManager.addFavourites(savedInstanceState.<WifiConfigurationDecorator>getParcelableArrayList(BUNDLE_KEY_FAVOURITES));


        adapter = new WifiExpandableListAdapter(
                getApplicationContext(),
                wifiManager.getFavourites(),
                wifiManager.getKnownNetworks());

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.wifi_list_view);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                WifiConfigurationDecorator requestedWifiConnection = adapter.getWifiDecorator(groupPosition, childPosition);
                wifiConnecitonManager.connect(requestedWifiConnection);
                return true;
            }
        });
        listView.expandGroup(WifiExpandableListAdapter.FAVOURITE_GROUP);
        listView.expandGroup(WifiExpandableListAdapter.KNOWN_NETWORK_GROUP);
        registerForContextMenu(listView);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(BUNDLE_KEY_FAVOURITES, wifiManager.getFavourites());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wifi_option_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
        //ListView sourceView = (ListView) info.targetView;
        //potential - a network becomes unknown do we remove it from the favourites
        switch (item.getItemId()) {
            case R.id.favourite:
                WifiConfigurationDecorator network = wifiConnecitonManager.getKnownNetwork((int) info.packedPosition);
                wifiManager.addFavourite(network);
                adapter.notifyDataSetChanged();
                break;
            default:
        }
        return false;
    }

}
