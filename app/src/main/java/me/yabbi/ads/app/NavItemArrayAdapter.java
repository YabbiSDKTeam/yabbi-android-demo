package me.yabbi.ads.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NavItemArrayAdapter extends ArrayAdapter<NavItem> {
    private final int layoutResId;

    public NavItemArrayAdapter(Context context, ArrayList<NavItem> ts) {
        this(context, R.layout.nav_list_item, ts);
    }

    public NavItemArrayAdapter(Context context, int listItemLayoutResourceId, ArrayList<NavItem> ts) {
        super(context, listItemLayoutResourceId, ts);
        layoutResId = listItemLayoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;
        if (null == convertView) {
            listItemView = inflater.inflate(layoutResId, parent, false);
        }

        TextView lineOneView = listItemView.findViewById(android.R.id.text1);
        TextView lineTwoView = listItemView.findViewById(android.R.id.text2);

        NavItem t = getItem(position);
        lineOneView.setText(lineOneText(t));
        lineTwoView.setText(lineTwoText(t));

        return listItemView;
    }

    public String lineOneText(NavItem e) {
        return e.title;
    }

    public String lineTwoText(NavItem e) {
        return e.description;
    }
}
