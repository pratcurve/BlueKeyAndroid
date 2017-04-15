package com.example.prateek.bluekey;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by prateek on 23/2/17.
 */

public class SearchBluetooth extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set<Device> foundDevices;
    public  List<Device> deviceList;
    private BluetoothDevice device;
    private DeviceAdapter deviceAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final String TAG = "SearchBluetooth";


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent.
            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String deviceName = device.getName();
//            Log.e("Name", deviceName);
            String deviceHardwareAddress = device.getAddress(); // MAC address
            if (!foundDevices.contains(deviceHardwareAddress)) {
                Device device = new Device(deviceName, deviceHardwareAddress);
                foundDevices.add(device);
                deviceList.add(device);
            }
            deviceAdapter.notifyDataSetChanged();
        }
    };

    public void discoverDevices(Context context) {
        deviceList = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(deviceList, context);
        foundDevices = new HashSet<>();
        // Register for broadcasts when a device is discovered.
        Log.e("Search Bluetooth", "Discovering bluetooth");
//        deviceList = new ArrayList<>();
        mBluetoothAdapter.startDiscovery();
        SearchActivity.recyclerView.setAdapter(deviceAdapter);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
    }


    public void connectDevice (String deviceAddress) {
        BluetoothDevice connectToDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
        ConnectThread connectThread = new ConnectThread(connectToDevice);
        Log.e("Connect Device Name", connectToDevice.getAddress());
        Log.e("Connect device add", connectToDevice.getName());
        connectThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mBluetoothAdapter.cancelDiscovery();
    }

    public class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        private BluetoothSocket mmSocket;
        UUID MY_UUID = UUID.randomUUID();

        public ConnectThread(BluetoothDevice device){

            mBluetoothAdapter.cancelDiscovery();
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                Log.i(TAG, mmDevice.toString());
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
                if(mmSocket.isConnected())
                    Toast.makeText(getApplicationContext(),"Connected", Toast.LENGTH_LONG).show();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                Log.i(TAG,"Not Connected");
                try {
                    Log.e("","trying fallback...");

                    mmSocket =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mmDevice,1);
                    mmSocket.connect();
                    Log.e("","Connected!");
                    finish();
                }
                catch (Exception e2) {
                    Log.e("", "Couldn't establish Bluetooth connection!");
                }
                connectException.printStackTrace();
            }
        }
    }
}
