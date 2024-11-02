package me.yabbi.ads.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class BaseActivity extends AppCompatActivity {
    private AutoRotateObserver autoRotateObserver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        enableBackButton();

        // Устанавливаем ориентацию при запуске Activity
        updateOrientation();

        // Создаем и регистрируем ContentObserver для отслеживания изменений в настройках автоповорота
        autoRotateObserver = new AutoRotateObserver(new Handler(), this::updateOrientation);
        getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION),
                false,
                autoRotateObserver
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }

    private void enableBackButton() {
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // Метод для обновления ориентации экрана
    private void updateOrientation() {
        boolean isAutoRotateEnabled = Settings.System.getInt(
                getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                0
        ) == 1;

        if (isAutoRotateEnabled) {
            // Разрешаем свободную ориентацию при включенном автоповороте
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            // Фиксируем в портретной при выключенном автоповороте
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Проверяем, что autoRotateObserver не равен null перед отменой регистрации
        if (autoRotateObserver != null) {
            getContentResolver().unregisterContentObserver(autoRotateObserver);
        }
    }
}
