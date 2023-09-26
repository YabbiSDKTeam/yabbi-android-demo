package me.yabbi.ads.app;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.Objects;

import me.yabbi.ads.YabbiAds;
import me.yabbi.ads.YbiInterstitialListener;
import me.yabbi.ads.common.AdException;

public class InterstitialActivity extends AdvertActivity implements YbiInterstitialListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        setPlacementName(EnvironmentVariables.yabbiInterstitialUnitID);
        setMediation();
        YabbiAds.setInterstitialListener(this);
    }

    @Override
    public void onInterstitialLoaded() {
        addLog("onInterstitialLoaded: Ad loaded and ready to show.");
    }

    @Override
    public void onInterstitialLoadFail(AdException error) {
        addLog("onInterstitialLoadFail: Ad was not loaded." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onInterstitialShown() {
        addLog("onInterstitialShown: Ad shown.");
    }

    @Override
    public void onInterstitialShowFailed(AdException error) {
        addLog("onInterstitialShowFailed: Ad was not shown." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onInterstitialClosed() {
        addLog("onInterstitialClosed: Ad closed.");
    }

    @Override
    public void selectPlacementName(String network) {
        final Resources resources = getResources();
        final String yabbi = resources.getString(R.string.yabbi);
        final String yandex = resources.getString(R.string.yandex);
        final String ironsource = resources.getString(R.string.ironsource);
        final String mintegral = resources.getString(R.string.mintegral);

        if(Objects.equals(network, yabbi)) {
            setPlacementName(EnvironmentVariables.yabbiInterstitialUnitID);
        }else if(Objects.equals(network, yandex)) {
            setPlacementName(EnvironmentVariables.yandexInterstitialUnitID);
        }else if(Objects.equals(network, ironsource)) {
            setPlacementName(EnvironmentVariables.ironsourceInterstitialUnitID);
        }else if(Objects.equals(network, mintegral)) {
            setPlacementName(EnvironmentVariables.mintegralInterstitialUnitID);
        }
    }

    @Override
    public void loadAd() {
        if (YabbiAds.canLoadAd(YabbiAds.INTERSTITIAL, getPlacementName())) {
            addLog("Ad start to load.");
            YabbiAds.loadAd(this, YabbiAds.INTERSTITIAL, getPlacementName());
        } else {
            addLog("SDK can't start load ad.");
        }
    }

    @Override
    public void showAd() {
        if (YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL, getPlacementName())) {
            YabbiAds.showAd(this, YabbiAds.INTERSTITIAL, getPlacementName());
        } else {
            addLog("Ad is not loaded yet");
        }
    }

    @Override
    public void destroyAd() {
        YabbiAds.destroyAd(YabbiAds.INTERSTITIAL, getPlacementName());
        addLog("Ad was destroyed.");
    }
}