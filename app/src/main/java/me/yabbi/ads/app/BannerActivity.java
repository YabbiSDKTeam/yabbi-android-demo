package me.yabbi.ads.app;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.Objects;

import sspnet.tech.core.AdPayload;
import sspnet.tech.core.BannerListener;
import sspnet.tech.unfiled.AdException;
import sspnet.tech.unfiled.ExternalInfoStrings;
import sspnet.tech.yabbi.Yabbi;

public class BannerActivity extends AdvertActivity implements BannerListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_layout);
        setPlacementName(EnvironmentVariables.yabbiBannerUnitID);
        setMediation();
        Yabbi.setBannerListener(this);
        Yabbi.setCustomParams(ExternalInfoStrings.yandexBannerUnitID, "demo-banner-yandex");
    }

    @Override
    public void onBannerLoaded(AdPayload adPayload) {
        addLog("onBannerLoaded: Ad loaded and ready to show.");
    }

    @Override
    public void onBannerLoadFailed(AdPayload adPayload, AdException error) {
        addLog("onBannerLoadFail: Ad was not loaded." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onBannerShown(AdPayload adPayload) {
        addLog("onBannerShown: Ad shown.");
    }

    @Override
    public void onBannerShowFailed(AdPayload adPayload, AdException error) {
        addLog("onBannerShowFailed: Ad was not shown." + error.getDescription() + " " + error.getCaused() + ".");
    }

    @Override
    public void onBannerClosed(AdPayload adPayload) {
        addLog("onBannerClosed:  Ad closed.");
    }

    @Override
    public void onBannerImpression(AdPayload adPayload) {
        addLog("onBannerImpression: Banner get impression.");
    }

    @Override
    public void selectPlacementName(String network) {
        final Resources resources = getResources();
        final String yabbi = resources.getString(R.string.yabbi);
        final String yandex = resources.getString(R.string.yandex);
        final String ironsource = resources.getString(R.string.ironsource);
        final String mintegral = resources.getString(R.string.mintegral);

        if (Objects.equals(network, yabbi)) {
            setPlacementName(EnvironmentVariables.yabbiBannerUnitID);
        } else {
            setPlacementName("");
        }
    }

    @Override
    public void loadAd() {
        if (Yabbi.canLoadAd(Yabbi.BANNER, getPlacementName())) {
            addLog("Ad start to load.");
            Yabbi.setCustomParams(ExternalInfoStrings.yandexBannerWidth, findViewById(R.id.banner_view).getWidth());
            Yabbi.setBannerLayoutID(R.id.banner_view);
            Yabbi.loadAd(this, Yabbi.BANNER, getPlacementName());
        } else {
            addLog("SDK can't start load ad.");
        }
    }

    @Override
    public void showAd() {
        if (Yabbi.isAdLoaded(Yabbi.BANNER, getPlacementName())) {
            Yabbi.showAd(this, Yabbi.BANNER, getPlacementName());
        } else {
            addLog("Ad is not loaded yet");
        }
    }

    @Override
    public void destroyAd() {
        Yabbi.destroyAd(Yabbi.BANNER, getPlacementName());
        addLog("Ad was destroyed.");
    }
}