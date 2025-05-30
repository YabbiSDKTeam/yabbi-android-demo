package me.yabbi.ads.app;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import sspnet.tech.core.InitializationListener;
import sspnet.tech.unfiled.AdException;
import sspnet.tech.yabbi.Yabbi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            final int color = getResources().getColor(R.color.background);
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
            actionBar.setElevation(0);
        }

        final TextView sdkVersion = findViewById(R.id.textView12);
        sdkVersion.setText(String.format("v%s", Yabbi.getSdkVersion()));

        final ListView advertLV = findViewById(R.id.listView1);
        final ListView consentLV = findViewById(R.id.listView2);
        final ListView infoLV = findViewById(R.id.listView3);

        final ArrayList<NavItem> advertNavItems = new ArrayList<>();
        final ArrayList<NavItem> consentNavItems = new ArrayList<>();
        final ArrayList<NavItem> infoNavItems = new ArrayList<>();

        final String interstitialTitle = getResources().getString(R.string.interstitial);
        final String rewardedVideoTitle = getResources().getString(R.string.rewarded_video);
        final String bannerTitle = getResources().getString(R.string.banner);
        final String consentManagerTitle = getResources().getString(R.string.consent_manager);
        final String deviceInfoTitle = getResources().getString(R.string.device_info_lower);

        advertNavItems.add(new NavItem(interstitialTitle, "Full screen ads. Graphic or video"));
        advertNavItems.add(new NavItem(rewardedVideoTitle, "Full screen rewarded ads"));
        advertNavItems.add(new NavItem(bannerTitle, "Banner ads"));

        consentNavItems.add(new NavItem(consentManagerTitle, "Ask user permissions"));
        infoNavItems.add(new NavItem(deviceInfoTitle, "GAID, UserAgent, etc."));

        NavItemArrayAdapter advertAdapter = new NavItemArrayAdapter(this, advertNavItems);
        NavItemArrayAdapter consentAdapter = new NavItemArrayAdapter(this, consentNavItems);
        NavItemArrayAdapter infoAdapter = new NavItemArrayAdapter(this, infoNavItems);

        advertLV.setAdapter(advertAdapter);
        consentLV.setAdapter(consentAdapter);
        infoLV.setAdapter(infoAdapter);

        advertLV.setOnItemClickListener((listView, itemView, itemPosition, itemId) -> {
            switch (itemPosition) {
                case 0:
                    final Intent interstitial = new Intent(MainActivity.this, InterstitialActivity.class);
                    startActivity(interstitial);
                    break;
                case 1:
                    final Intent rewarded = new Intent(MainActivity.this, RewardedActivity.class);
                    startActivity(rewarded);
                    break;
                case 2:
                    final Intent banner = new Intent(MainActivity.this, BannerActivity.class);
                    startActivity(banner);
                    break;
                default:
                    break;
            }
        });

        consentLV.setOnItemClickListener((listView, itemView, itemPosition, itemId) -> {
            if (itemPosition == 0) {
                final Intent intent = new Intent(MainActivity.this, ConsentManagerActivity.class);
                startActivity(intent);
            }
        });

        infoLV.setOnItemClickListener((listView, itemView, itemPosition, itemId) -> {
            if (itemPosition == 0) {
                final Intent intent = new Intent(MainActivity.this, DeviceInfoActivity.class);
                startActivity(intent);
            }
        });

        Yabbi.enableDebug(true);
        Yabbi.initialize(EnvironmentVariables.publisherID, new InitializationListener() {
            @Override
            public void onInitializeSuccess() {

            }

            @Override
            public void onInitializeFailed(AdException e) {

            }
        });
    }
}