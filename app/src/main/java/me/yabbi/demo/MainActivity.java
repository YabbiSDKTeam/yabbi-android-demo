package me.yabbi.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.yabbi.ads.YabbiAds;
import me.yabbi.ads.YabbiConfiguration;
import me.yabbi.ads.YbiInterstitialListener;
import me.yabbi.ads.YbiRewardedListener;
import me.yabbi.ads.common.YbiAdaptersParameters;
import me.yabbi.ads.consent.YbiConsentBuilder;
import me.yabbi.ads.consent.YbiConsentListener;
import me.yabbi.ads.consent.YbiConsentManager;

public class MainActivity extends AppCompatActivity {


    final YbiConsentManager consentManager = YbiConsentManager.getInstance(this);

    private TextView logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtons();
        setListeners();

        initializeYabbi();
    }

    private void setButtons() {
        findViewById(R.id.load_interstitial_button).setOnClickListener(v -> YabbiAds.loadAd(this, YabbiAds.INTERSTITIAL));
        findViewById(R.id.show_interstitial_button).setOnClickListener(v -> YabbiAds.showAd(this, YabbiAds.INTERSTITIAL));
        findViewById(R.id.destroy_interstitial_button).setOnClickListener(v -> YabbiAds.destroyAd(YabbiAds.INTERSTITIAL));

        findViewById(R.id.load_rewarded_button).setOnClickListener(v -> YabbiAds.loadAd(this, YabbiAds.REWARDED));
        findViewById(R.id.show_rewarded_button).setOnClickListener(v -> YabbiAds.showAd(this, YabbiAds.REWARDED));
        findViewById(R.id.destroy_rewarded_button).setOnClickListener(v -> YabbiAds.destroyAd(YabbiAds.REWARDED));

        findViewById(R.id.show_consent_button).setOnClickListener(v -> consentManager.showConsentWindow());

        logger = findViewById(R.id.events);
    }

    private void initializeYabbi() {
        YabbiAds.setCustomParams(YbiAdaptersParameters.yandexInterstitialID, BuildConfig.YANDEX_INTERSTITIAL_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.yandexRewardedID, BuildConfig.YANDEX_REWARDED_ID);

        YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralAppID, BuildConfig.MINTEGRAL_APP_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralApiKey, BuildConfig.MINTEGRAL_API_KEY);
        YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralInterstitialPlacementId, BuildConfig.MINTEGRAL_INTERSTITIAL_PLACEMENT_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralInterstitialUnitId, BuildConfig.MINTEGRAL_INTERSTITIAL_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralRewardedPlacementId, BuildConfig.MINTEGRAL_REWARDED_PLACEMENT_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralRewardedUnitId, BuildConfig.MINTEGRAL_REWARDED_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.ironSourceAppID, BuildConfig.IRON_SOURCE_APP_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.ironSourceInterstitialPlacementID, BuildConfig.IRON_SOURCE_INTERSTITIAL_ID);
        YabbiAds.setCustomParams(YbiAdaptersParameters.ironSourceRewardedPlacementID, BuildConfig.IRON_SOURCE_REWARDED_ID);

        final YabbiConfiguration config = new YabbiConfiguration(BuildConfig.YABBI_PUBLISHER_ID, BuildConfig.YABBI_INTERSTITIAL_ID, BuildConfig.YABBI_REWARDED_ID);

        YabbiAds.setUserConsent(consentManager.hasConsent());

        YabbiAds.enableDebug(true);
        YabbiAds.initialize(config);

        logEvent("YabbiAds initialized");

        consentManager.loadManager();
    }

    private void setListeners() {
        final YbiConsentBuilder builder = new YbiConsentBuilder()
                .appendGDPR(true)
                .appendName("Demo app name");
        consentManager.registerCustomVendor(builder);
        consentManager.setListener(new YbiConsentListener() {
            @Override
            public void onConsentManagerLoaded() {
                logEvent("onConsentManagerLoaded");
            }

            @Override
            public void onConsentManagerLoadFailed(String error) {
                logEvent("onConsentManagerLoadFailed - " + error);
            }

            @Override
            public void onConsentWindowShown() {
                logEvent("onConsentWindowShown");
            }

            @Override
            public void onConsentManagerShownFailed(String error) {
                logEvent("onConsentManagerShownFailed - " + error);
            }

            @Override
            public void onConsentWindowClosed(boolean hasConsent) {
                YabbiAds.setUserConsent(hasConsent);
                logEvent("onConsentWindowClosed - Has User Consent: " + hasConsent);
            }
        });

        YabbiAds.setInterstitialListener(new YbiInterstitialListener() {
            @Override
            public void onInterstitialLoaded() {
                logEvent("onInterstitialLoaded");
            }

            @Override
            public void onInterstitialLoadFail(String error) {
                logEvent("onInterstitialLoadFail: " + error);
            }

            @Override
            public void onInterstitialShown() {
                logEvent("onInterstitialShown");
            }

            @Override
            public void onInterstitialShowFailed(String error) {
                logEvent("onInterstitialShowFailed: " + error);
            }

            @Override
            public void onInterstitialClosed() {
                logEvent("onInterstitialClosed");
            }
        });

        YabbiAds.setRewardedListener(new YbiRewardedListener() {
            @Override
            public void onRewardedLoaded() {
                logEvent("onRewardedLoaded");
            }

            @Override
            public void onRewardedLoadFail(String error) {
                logEvent("onRewardedLoadFail: " + error);
            }

            @Override
            public void onRewardedShown() {
                logEvent("onRewardedShown");
            }

            @Override
            public void onRewardedShowFailed(String error) {
                logEvent("onRewardedShowFailed: " + error);
            }

            @Override
            public void onRewardedClosed() {
                logEvent("onRewardedClosed");
            }

            @Override
            public void onRewardedFinished() {
                logEvent("onRewardedFinished");
            }
        });
    }

    private void logEvent(String message) {
        final String text = logger.getText().toString();
        logger.setText(String.format("%s\n%s", text, message));
    }
}