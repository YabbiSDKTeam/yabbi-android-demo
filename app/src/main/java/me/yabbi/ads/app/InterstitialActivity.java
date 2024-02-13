package me.yabbi.ads.app;

import android.content.res.Resources;
import android.os.Bundle;
import java.util.Objects;

import sspnet.tech.core.AdPayload;
import sspnet.tech.core.InterstitialListener;
import sspnet.tech.unfiled.AdException;
import sspnet.tech.unfiled.ExternalInfoStrings;
import sspnet.tech.yabbi.Yabbi;
public class InterstitialActivity extends AdvertActivity implements InterstitialListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        setPlacementName(EnvironmentVariables.yabbiInterstitialUnitID);
        setMediation();
        Yabbi.setInterstitialListener(this);
    }

    @Override
    public void onInterstitialLoaded(AdPayload adPayload) {
        addLog("onInterstitialLoaded: Ad loaded and ready to show.");
    }

    @Override
    public void onInterstitialLoadFail(AdPayload adPayload, AdException error) {
        addLog("onInterstitialLoadFail: Ad was not loaded." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onInterstitialShown(AdPayload adPayload) {
        addLog("onInterstitialShown: Ad shown.");
    }

    @Override
    public void onInterstitialShowFailed(AdPayload adPayload, AdException error) {
        addLog("onInterstitialShowFailed: Ad was not shown." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onInterstitialClosed(AdPayload adPayload) {
        addLog("onInterstitialClosed: Ad closed.");
    }

    @Override
    public void selectPlacementName(String network) {
        final Resources resources = getResources();
        final String yabbi = resources.getString(R.string.yabbi);
        final String yandex = resources.getString(R.string.yandex);
        final String ironsource = resources.getString(R.string.ironsource);
        final String mintegral = resources.getString(R.string.mintegral);
        final String applovin = resources.getString(R.string.applovin);


        if(Objects.equals(network, yabbi)) {
            setPlacementName(EnvironmentVariables.yabbiInterstitialUnitID);
            Yabbi.setCustomParams(ExternalInfoStrings.applovinInterstitialUnitID, null);
        }else if(Objects.equals(network, yandex)) {
            setPlacementName(EnvironmentVariables.yandexInterstitialUnitID);
            Yabbi.setCustomParams(ExternalInfoStrings.applovinInterstitialUnitID, null);
        }else if(Objects.equals(network, ironsource)) {
            setPlacementName(EnvironmentVariables.ironsourceInterstitialUnitID);
            Yabbi.setCustomParams(ExternalInfoStrings.applovinInterstitialUnitID, null);
        }else if(Objects.equals(network, mintegral)) {
            setPlacementName(EnvironmentVariables.mintegralInterstitialUnitID);
            Yabbi.setCustomParams(ExternalInfoStrings.applovinInterstitialUnitID, null);
        }else if(Objects.equals(network, applovin)) {
            setPlacementName(EnvironmentVariables.ironsourceInterstitialUnitID);
            Yabbi.setCustomParams(ExternalInfoStrings.applovinInterstitialUnitID, "123");
        }
    }

    @Override
    public void loadAd() {
        if (Yabbi.canLoadAd(Yabbi.INTERSTITIAL, getPlacementName())) {
            addLog("Ad start to load.");
            Yabbi.loadAd(this, Yabbi.INTERSTITIAL, getPlacementName());
        } else {
            addLog("SDK can't start load ad.");
        }
    }

    @Override
    public void showAd() {
        if (Yabbi.isAdLoaded(Yabbi.INTERSTITIAL, getPlacementName())) {
            Yabbi.showAd(this, Yabbi.INTERSTITIAL, getPlacementName());
        } else {
            addLog("Ad is not loaded yet");
        }
    }

    @Override
    public void destroyAd() {
        Yabbi.destroyAd(Yabbi.INTERSTITIAL, getPlacementName());
        addLog("Ad was destroyed.");
    }
}