package me.yabbi.ads.app;

import android.content.res.Resources;
import android.os.Bundle;
import java.util.Objects;
import sspnet.tech.core.InterstitialListener;
import sspnet.tech.unfiled.AdException;
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