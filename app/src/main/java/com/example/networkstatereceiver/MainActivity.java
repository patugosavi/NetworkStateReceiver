package com.example.networkstatereceiver;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNetworkBroadcastReceiver(this);
    }

    @Override
    protected void onPause() {
        /***/
        unregisterNetworkBroadcastReceiver(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        /***/
        registerNetworkBroadcastReceiver(this);
        super.onResume();
    }

    @Override
    public void networkAvailable() {
        Log.i(TAG, "networkAvailable()");
        Toast.makeText(getApplicationContext(), "networkAvailable", Toast.LENGTH_SHORT).show();
        //Proceed with online actions in activity (e.g. hide offline UI from user, start services, etc...)
    }

    @Override
    public void networkUnavailable() {
        Log.i(TAG, "networkUnavailable()");
        Toast.makeText(getApplicationContext(), "networkUnavailable", Toast.LENGTH_SHORT).show();
        //Proceed with offline actions in activity (e.g. sInform user they are offline, stop services, etc...)
    }

    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    /**
     * Register the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     Unregister the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }
}