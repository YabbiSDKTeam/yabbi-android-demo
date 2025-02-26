package me.yabbi.ads.app;

import android.database.ContentObserver;
import android.os.Handler;

public class AutoRotateObserver extends ContentObserver {
    private final OnAutoRotateChangeListener listener;

    public AutoRotateObserver(Handler handler, OnAutoRotateChangeListener listener) {
        super(handler);
        this.listener = listener;
    }

    @Override
    public void onChange(boolean selfChange) {
        // Уведомляем слушателя об изменении автоповорота
        listener.onAutoRotateChanged();
    }

    public interface OnAutoRotateChangeListener {
        void onAutoRotateChanged();
    }
}
