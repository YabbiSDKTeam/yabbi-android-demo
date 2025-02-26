package me.yabbi.ads.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeviceInfoArrayAdapter extends ArrayAdapter<DeviceInfoItem> {
    private final int layoutResId;
    private ClipboardManager clipboardManager;


    public DeviceInfoArrayAdapter(Context context, ArrayList<DeviceInfoItem> ts) {
        this(context, R.layout.device_info_list_item, ts);
    }

    public DeviceInfoArrayAdapter(Context context, int listItemLayoutResourceId, ArrayList<DeviceInfoItem> ts) {
        super(context, listItemLayoutResourceId, ts);
        layoutResId = listItemLayoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        View listItemView = convertView;
        if (null == convertView) {
            listItemView = inflater.inflate(layoutResId, parent, false);
        }

        final TextView lineOneView = listItemView.findViewById(android.R.id.text1);
        final TextView lineTwoView = listItemView.findViewById(android.R.id.text2);
        final Button button = listItemView.findViewById(R.id.copy_button);

        DeviceInfoItem t = getItem(position);
        lineOneView.setText(lineOneText(t));
        lineTwoView.setText(lineTwoText(t));

        button.setOnClickListener(v -> {
            copyTextToClipboard(lineTwoText(t));

        });

        return listItemView;
    }

    public String lineOneText(DeviceInfoItem e) {
        return e.title;
    }

    public String lineTwoText(DeviceInfoItem e) {
        return e.description;
    }

    private void copyTextToClipboard(String text) {
        ClipData clipData = ClipData.newPlainText("text", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "Copied!", Toast.LENGTH_LONG).show();
    }

}
