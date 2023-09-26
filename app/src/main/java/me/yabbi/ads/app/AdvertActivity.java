package me.yabbi.ads.app;

import android.widget.Button;
import android.widget.PopupMenu;

import me.yabbi.ads.YabbiAds;

public abstract class AdvertActivity extends EventsActivity {

    private String placementName = "";

    public void setMediation() {
        setEvents();

        final Button loadAd = findViewById(R.id.load_ad);
        final Button showAd = findViewById(R.id.show_ad);
        final Button destroyAd = findViewById(R.id.destroy_ad);
        final Button mediationButton = findViewById(R.id.mediation);

        mediationButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(AdvertActivity.this, mediationButton);

            popupMenu.getMenuInflater().inflate(R.menu.mediation_networks_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                final String title = String.valueOf(item.getTitle());
                mediationButton.setText(title);
                selectPlacementName(title);
                addLog("Ad will be loaded from " + title + ".");
                return true;
            });
            popupMenu.show();
        });
        loadAd.setOnClickListener(v -> loadAd());
        showAd.setOnClickListener(v -> showAd());
        destroyAd.setOnClickListener(v -> destroyAd());

        clearLog();
    }

    public String getPlacementName() {
        return placementName;
    }

    public void setPlacementName(String placementName) {
        this.placementName = placementName;
    }

    public abstract void selectPlacementName(String network);

    public abstract void loadAd();

    public abstract void showAd();

    public abstract void destroyAd();

    @Override
    void clearLog() {
        setText("* YabbiAds " + YabbiAds.getSdkVersion() + " initialized.");
    }
}
