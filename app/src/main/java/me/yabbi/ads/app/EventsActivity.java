package me.yabbi.ads.app;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class EventsActivity extends AppCompatActivity {
    private TextView events;

    void setEvents() {
        events = findViewById(R.id.events);
        final Button button = findViewById(R.id.clear_events);
        button.setOnClickListener(v -> clearLog());
    }

    @SuppressLint("SetTextI18n")
    void clearLog() {
        events.setText("");
    }

    void setText(String message) {
        events.setText(message);
    }

    @SuppressLint("SetTextI18n")
    void addLog(String message) {
        final String text = String.valueOf(events.getText());

        String separator = "\n";

        if (text.isEmpty()) {
            separator = "";
        }

        events.setText(text + separator + "* " + message);
    }
}
