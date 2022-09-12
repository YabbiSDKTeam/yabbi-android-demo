package me.yabbi.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import me.yabbi.ads.YabbiAds;
import me.yabbi.ads.YabbiConfiguration;
import me.yabbi.ads.common.InterstitialAdCallbacks;
import me.yabbi.ads.common.RewardedAdCallbacks;

public class MainActivity extends AppCompatActivity {
    private EditText publisherField;
    private EditText interstitialField;
    private EditText rewardedField;
    private TextView logger;


    private String YABBI_PUBLISHER_ID;
    private String YABBI_INTERSTITIAL_ID;
    private String YABBI_REWARDED_ID;
    private String YANDEX_INTERSTITIAL_ID;
    private String YANDEX_REWARDED_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupKeyboardListener(findViewById(R.id.fields).getRootView());
        setupVariables();
        setupUI();
        setupYabbi();
        setupYabbiCallbacks();

    }

    private void setupVariables() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(this.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            YABBI_PUBLISHER_ID = info.metaData.getString("YABBI_PUBLISHER_VALUE", "");
            YABBI_INTERSTITIAL_ID = info.metaData.getString("YABBI_INTERSTITIAL_VALUE", "");
            YABBI_REWARDED_ID = info.metaData.getString("YABBI_REWARDED_VALUE", "");
            YANDEX_INTERSTITIAL_ID = info.metaData.getString("YANDEX_INTERSTITIAL_VALUE", "");
            YANDEX_REWARDED_ID = info.metaData.getString("YANDEX_REWARDED_VALUE", "");

        } catch (PackageManager.NameNotFoundException e) {
           e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case YabbiAds.INTERSTITIAL:
                YabbiAds.loadAd(this, YabbiAds.INTERSTITIAL);
                writeLog("Inerstitial loading start", true);
                break;
            case YabbiAds.REWARDED:
                YabbiAds.loadAd(this, YabbiAds.REWARDED);
                writeLog("Rewarded loading start", true);
                break;
        }
    }

    private void setupUI() {
        publisherField = findViewById(R.id.publisher_filed);
        interstitialField = findViewById(R.id.interstitial_field);
        rewardedField = findViewById(R.id.rewarded_ad);

        Button resetButton = findViewById(R.id.reset_button);
        Button interstitialButton = findViewById(R.id.interstitial_button);
        Button rewardedButton = findViewById(R.id.rewarded_button);

        logger = findViewById(R.id.logger);

        publisherField.setText(YABBI_PUBLISHER_ID);
        interstitialField.setText(YABBI_INTERSTITIAL_ID);
        rewardedField.setText(YABBI_REWARDED_ID);

        resetButton.setOnClickListener(
                v -> setupYabbi()
        );

        interstitialButton.setOnClickListener(
                v -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, YabbiAds.INTERSTITIAL);
                    }
                }
        );

        rewardedButton.setOnClickListener(
                v -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, YabbiAds.REWARDED);
                    }
                }
        );
    }

    @SuppressLint("SetTextI18n")
    private void setupYabbi() {
        final String publisherID = publisherField.getText().toString();
        final String interstitialID = interstitialField.getText().toString();
        final String rewardedID = rewardedField.getText().toString();

        final YabbiConfiguration config = new YabbiConfiguration(publisherID, interstitialID, rewardedID);
        YabbiAds.initialize(config);
        YabbiAds.setCustomParams("yandex_interstitial_id", YANDEX_INTERSTITIAL_ID);
        YabbiAds.setCustomParams("yandex_rewarded_id", YANDEX_REWARDED_ID);


        writeLog("PublisherID: " + publisherID + "\nInterstitialID: " + interstitialID + "\nRewardedID: " + rewardedID, true);
    }

    private void setupYabbiCallbacks() {
        YabbiAds.setInterstitialCallbacks(new InterstitialAdCallbacks() {
            @Override
            public void onInterstitialLoaded() {
                writeLog("onInterstitialLoaded", false);
                if (YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL))
                    YabbiAds.showAd(MainActivity.this, YabbiAds.INTERSTITIAL);
            }

            @Override
            public void onInterstitialLoadFail(String s) {
                writeLog("onInterstitialLoadFail: " + s, false);
            }

            @Override
            public void onInterstitialShown() {
                writeLog("onInterstitialShown", false);
            }

            @Override
            public void onInterstitialShowFailed(String s) {
                writeLog("onInterstitialShowFailed: " + s, false);
            }

            @Override
            public void onInterstitialClosed() {
                writeLog("onInterstitialClosed", false);
            }
        });

        YabbiAds.setRewardedCallbacks(new RewardedAdCallbacks() {
            @Override
            public void onRewardedLoaded() {
                writeLog("onRewardedLoaded", false);
                if (YabbiAds.isAdLoaded(YabbiAds.REWARDED))
                    YabbiAds.showAd(MainActivity.this, YabbiAds.REWARDED);
            }

            @Override
            public void onRewardedLoadFail(String s) {
                writeLog("onRewardedLoadFail: " + s, false);
            }

            @Override
            public void onRewardedShown() {
                writeLog("onRewardedShown", false);
            }

            @Override
            public void onRewardedShowFailed(String s) {
                writeLog("onRewardedShowFailed: " + s, false);
            }

            @Override
            public void onRewardedClosed() {
                writeLog("onRewardedClosed", false);
            }

            @Override
            public void onRewardedFinished() {
                writeLog("onRewardedFinished", false);
            }
        });
    }

    public void setupKeyboardListener(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupKeyboardListener(innerView);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void writeLog(String message, boolean clear) {
        if (clear) {
            logger.setText(message);
        } else {
            final String text = logger.getText().toString();
            logger.setText(text + "\n" + message);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}