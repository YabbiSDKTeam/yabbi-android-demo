package me.yabbi.ads.app;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.Objects;

import me.yabbi.ads.YabbiAds;
import me.yabbi.ads.YbiRewardedListener;
import me.yabbi.ads.common.AdException;

public class RewardedActivity extends AdvertActivity implements YbiRewardedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        setPlacementName(EnvironmentVariables.yabbiRewardedUnitID);
        setMediation();
        YabbiAds.setRewardedListener(this);
    }

    @Override
    public void onRewardedLoaded() {
        addLog("onRewardedLoaded: Ad loaded and ready to show.");
    }

    @Override
    public void onRewardedLoadFail(AdException error) {
        addLog("onRewardedLoadFail: Ad was not loaded." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onRewardedShown() {
        addLog("onRewardedShown: Ad shown.");
    }

    @Override
    public void onRewardedShowFailed(AdException error) {
        addLog("onRewardedShowFailed: Ad was not shown." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onRewardedClosed() {
        addLog("onRewardedClosed: Ad closed.");
    }

    @Override
    public void onRewardedFinished() {
        addLog("onRewardedFinished: Ad was finished.");
    }

    @Override
    public void selectPlacementName(String network) {
        final Resources resources = getResources();
        final String yabbi = resources.getString(R.string.yabbi);
        final String yandex = resources.getString(R.string.yandex);
        final String ironsource = resources.getString(R.string.ironsource);
        final String mintegral = resources.getString(R.string.mintegral);

        if(Objects.equals(network, yabbi)) {
            setPlacementName(EnvironmentVariables.yabbiRewardedUnitID);
        }else if(Objects.equals(network, yandex)) {
            setPlacementName(EnvironmentVariables.yandexRewardedlUnitID);
        }else if(Objects.equals(network, ironsource)) {
            setPlacementName(EnvironmentVariables.ironsourceRewardedlUnitID);
        }else if(Objects.equals(network, mintegral)) {
            setPlacementName(EnvironmentVariables.mintegraleRewardedlUnitID);
        }
    }

    @Override
    public void loadAd() {
        if (YabbiAds.canLoadAd(YabbiAds.REWARDED, getPlacementName())) {
            addLog("Ad start to load.");
            YabbiAds.loadAd(this, YabbiAds.REWARDED, getPlacementName());
        } else {
            addLog("SDK can't start load ad.");
        }
    }

    @Override
    public void showAd() {
        if (YabbiAds.isAdLoaded(YabbiAds.REWARDED, getPlacementName())) {
            YabbiAds.showAd(this, YabbiAds.REWARDED, getPlacementName());
        } else {
            addLog("Ad is not loaded yet");
        }
    }

    @Override
    public void destroyAd() {
        YabbiAds.destroyAd(YabbiAds.REWARDED, getPlacementName());
        addLog("Ad was destroyed.");
    }
}