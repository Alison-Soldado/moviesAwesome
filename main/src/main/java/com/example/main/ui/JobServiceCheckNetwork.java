package com.example.main.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

@SuppressLint("Registered")
public class JobServiceCheckNetwork extends JobService {

    ConnectivityManager.NetworkCallback networkCallback;
    BroadcastReceiver connectivityChange;
    ConnectivityManager connectivityManager;

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {
        Log.d("network", "Job created");
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), networkCallback = new ConnectivityManager.NetworkCallback(){
                // -Snip-
            });
        }else{
            registerReceiver(connectivityChange = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    handleConnectivityChange(!intent.hasExtra("noConnectivity"), intent.getIntExtra("networkType", -1));
                }
                }, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Log.d("network", "No active network.");
        }else{

        }
        Log.d("network", "Done with onStartJob");
        return true;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        if(networkCallback != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)connectivityManager.unregisterNetworkCallback(networkCallback);
        else if(connectivityChange != null)unregisterReceiver(connectivityChange);
        return true;
    }

    private void handleConnectivityChange(NetworkInfo networkInfo){
        // Calls handleConnectivityChange(boolean connected, int type)
    }

    private void handleConnectivityChange(boolean connected, int type){
        // Calls handleConnectivityChange(boolean connected, ConnectionType connectionType)
    }

    private void handleConnectivityChange(boolean connected, ConnectionType connectionType){
        // Logic based on the new connection
    }

    private enum ConnectionType{
        MOBILE,WIFI,VPN,OTHER;
    }
}
