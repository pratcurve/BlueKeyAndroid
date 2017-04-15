package com.example.prateek.bluekey;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by prateek on 23/2/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> deviceList;
    private Context context;
    private String email;

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceText;

        public DeviceViewHolder(View view){
            super(view);
            deviceText = (TextView) view.findViewById(R.id.search_device_textview);
        }
    }

    public DeviceAdapter (List<Device> deviceList, Context context) {
        this.deviceList = deviceList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(DeviceAdapter.DeviceViewHolder holder, final int position) {
        Device device = deviceList.get(position);
        holder.deviceText.setText(device.getLockName());
        holder.deviceText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkDeviceRegistration(deviceList.get(position).getLockAddress(), deviceList.get(position).getLockName());
                        SearchBluetooth searchBluetooth = new SearchBluetooth();
                        searchBluetooth.connectDevice(deviceList.get(position).getLockAddress());
                    }
                }
        );
    }

    @Override
    public DeviceAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new DeviceAdapter.DeviceViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public void checkDeviceRegistration(final String deviceAddress, final String lockname){
        String url = "http://192.168.1.6/auto/register.php";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        email = prefs.getString("email", email);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    String success = responseObject.getString("success");
                    if (success.equals("1")) {
                      String defaultStatus = "0";
                        SaveLock saveLock = new SaveLock(lockname, deviceAddress, defaultStatus, context);
                        saveLock.saveToFile();
                    } else {
                        Log.e("SuccessResponse", "Already registered");
                    }
                } catch (JSONException e) {
                    Log.e("ErrorJson", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error register", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("address", deviceAddress);
                params.put("lockname", lockname);
                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}
