package com.android.andrew.wifi_selector.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.andrew.wifi_selector.R;
import com.android.andrew.wifi_selector.WifiConfigurationDecorator;
import com.android.andrew.wifi_selector.WifiConnectionManager;
import com.android.andrew.wifi_selector.WifiManager;

import static android.widget.AdapterView.*;

public class WifiAccessActivity extends AppCompatActivity {

    private WifiConnectionManager wifiConnecitonManager;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_access);
        wifiConnecitonManager = new WifiConnectionManager(getApplicationContext());
        //I need to save these network because if I call get again (somewhere else) they might have changed.
        wifiManager = new WifiManager();

        final ArrayAdapter<WifiConfigurationDecorator> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_list_item, wifiConnecitonManager.getKnownNetworks());

        ListView listView = (ListView) findViewById(R.id.wifi_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiConfigurationDecorator requestedWifiConnection = (WifiConfigurationDecorator) parent.getItemAtPosition(position);
                wifiConnecitonManager.connect(requestedWifiConnection);
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wifi_option_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        //ListView sourceView = (ListView) info.targetView;
        //potential - a network becomes unknown do we remove it from the favourites
        switch (item.getItemId()) {
            case R.id.favourite:
                wifiManager.addFavourite(wifiConnecitonManager.getKnownNetwork(info.position) );
                Toast.makeText(getApplicationContext(), " decorator values is " + wifiConnecitonManager.getKnownNetwork(info.position), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
