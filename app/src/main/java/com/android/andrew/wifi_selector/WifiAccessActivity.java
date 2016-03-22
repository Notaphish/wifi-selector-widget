package com.android.andrew.wifi_selector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WifiAccessActivity extends AppCompatActivity {

    private WifiConnectionManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_access);
        wifiManager = new WifiConnectionManager(getApplicationContext());

        final ArrayAdapter<WifiConfigurationDecorator> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_list_item, wifiManager.getKnownWifiNetworks());

        ListView listView = (ListView) findViewById(R.id.wifi_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiConfigurationDecorator requestedWifiConnection = (WifiConfigurationDecorator) parent.getItemAtPosition(position);
                wifiManager.connect(requestedWifiConnection);
            }
        });

    }

}
