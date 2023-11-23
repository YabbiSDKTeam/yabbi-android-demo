package me.yabbi.ads.app;

import android.content.res.Resources;
import android.os.Bundle;
import java.util.Objects;
import sspnet.tech.core.RewardedListener;
import sspnet.tech.unfiled.AdException;
import sspnet.tech.yabbi.Yabbi;

public class RewardedActivity extends AdvertActivity implements RewardedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        setPlacementName(EnvironmentVariables.yabbiRewardedUnitID);
        setMediation();
        Yabbi.setRewardedListener(this);
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
        if (Yabbi.canLoadAd(Yabbi.REWARDED, getPlacementName())) {
            addLog("Ad start to load.");
            Yabbi.loadAd(this, Yabbi.REWARDED, getPlacementName());
        } else {
            addLog("SDK can't start load ad.");
        }
    }

    @Override
    public void showAd() {
        if (Yabbi.isAdLoaded(Yabbi.REWARDED, getPlacementName())) {
            Yabbi.showAd(this, Yabbi.REWARDED, getPlacementName());
        } else {
            addLog("Ad is not loaded yet");
        }
    }

    @Override
    public void destroyAd() {
        Yabbi.destroyAd(Yabbi.REWARDED, getPlacementName());
        addLog("Ad was destroyed.");
    }
}