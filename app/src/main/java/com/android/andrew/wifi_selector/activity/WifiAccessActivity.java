package com.android.andrew.wifi_selector.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.andrew.wifi_selector.R;
import com.android.andrew.wifi_selector.WifiConfigurationDecorator;
import com.android.andrew.wifi_selector.WifiConnectionManager;
import com.android.andrew.wifi_selector.widget.WifiChoiceDialog;

public class WifiAccessActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                WifiChoiceDialog dialog = new WifiChoiceDialog();
                dialog.show(getSupportFragmentManager(), "something");
                WifiConfigurationDecorator item = (WifiConfigurationDecorator) parent.getItemAtPosition(position);
                return true;            }
        } );

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText( getApplicationContext(), "Clicked on something in main activity ", Toast.LENGTH_SHORT).show();
    }
}
