package me.yabbi.ads.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.yabbi.ads.YabbiAds;

public class DeviceInfoActivity extends AppCompatActivity {

    public static final String DEFAULT_IFA = "00000000-0000-0000-0000-000000000000";
    private static final String DEFAULT_ADDRESS = "02:00:00:00:00:00";

    final ArrayList<DeviceInfoItem> items = new ArrayList<>();;

    DeviceInfoArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        final ListView lv = findViewById(R.id.listView4);

        final String sdkVersion = "" + YabbiAds.getSdkVersion();
        final String androidOSVersion = "" + Build.VERSION.RELEASE;
        final String androidApiVersion = "" + Build.VERSION.SDK_INT;
        final String deviceModel = getDeviceName();
        final String macAddress = getMacAddress();
        final String userAgent = new WebView(this).getSettings().getUserAgentString();

        items.add(new DeviceInfoItem("YabbiAds SDK Version", sdkVersion));
        items.add(new DeviceInfoItem("Android Version", androidOSVersion));
        items.add(new DeviceInfoItem("Android API Version", androidApiVersion));
        items.add(new DeviceInfoItem("Device Model", deviceModel));
        items.add(new DeviceInfoItem("Wifi MacAdress", macAddress));
        items.add(new DeviceInfoItem("User Agent", userAgent));

        adapter = new DeviceInfoArrayAdapter(DeviceInfoActivity.this, items);
        lv.setAdapter(adapter);
        setAdvertisingId();
    }

    private void setAdvertisingId() {
        new Thread(() -> {
            try {
                AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(DeviceInfoActivity.this);
                items.add(new DeviceInfoItem("GAID", adInfo.getId()));
                this.runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            } catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
                items.add(new DeviceInfoItem("GAID",DEFAULT_IFA));
                this.runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }


    String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return DEFAULT_ADDRESS;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            return DEFAULT_ADDRESS;
        }
        return DEFAULT_ADDRESS;
    }

    String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}