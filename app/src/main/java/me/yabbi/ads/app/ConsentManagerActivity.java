package me.yabbi.ads.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import sspnet.tech.consent.core.ConsentBuilder;
import sspnet.tech.consent.core.ConsentListener;
import sspnet.tech.consent.yabbi.ConsentManager;
import sspnet.tech.yabbi.Yabbi;


public class ConsentManagerActivity extends EventsActivity implements ConsentListener {

    ConsentManager manager;
    SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_manager);
        setEvents();

        storage = getSharedPreferences("Demo App", Context.MODE_PRIVATE);

        final ConsentBuilder builder = new ConsentBuilder().appendPolicyURL("https://yabbi.me/privacy-policies");

        manager = new ConsentManager();
        manager.registerCustomVendor(builder);
        manager.setCustomStorage(storage);
        manager.setListener(this);

        final Button showGdprButton = findViewById(R.id.show_gdpr);
        final Button showNoGdprButton = findViewById(R.id.show_no_gdpr);

        showGdprButton.setOnClickListener(v -> showWindow(true));
        showNoGdprButton.setOnClickListener(v -> showWindow(false));

        addLog("Consent window initialized.");
    }

    void showWindow(boolean gdpr) {
        clearStorage();

        final ConsentBuilder builder = new ConsentBuilder().appendGDPR(gdpr);
        manager.registerCustomVendor(builder);
        manager.loadManager();
    }

    void clearStorage() {
        storage.edit().clear().apply();
    }

    @Override
    void clearLog() {
        setText("* Consent window initialized.");
    }

    @Override
    public void onConsentManagerLoaded() {
        addLog("onConsentManagerLoaded: Consent window ready to show.");
        manager.showConsentWindow(this);
    }

    @Override
    public void onConsentManagerLoadFailed(String message) {
        addLog("onConsentManagerLoadFailed: Consent window did not load." + message + ".");
    }

    @Override
    public void onConsentWindowShown() {
        addLog("onConsentWindowShown: Consent window shown.");
    }

    @Override
    public void onConsentManagerShownFailed(String message) {
        addLog("onConsentManagerShownFailed: Consent window did not load." + message + ".");
    }

    @Override
    public void onConsentWindowClosed(boolean hasConsent) {
        String prefix = "not";

        if (hasConsent) {
            prefix = "";
        }

        addLog("onConsentWindowClosed: User consent" + prefix + " received.");
        Yabbi.setUserConsent(hasConsent);
    }
}