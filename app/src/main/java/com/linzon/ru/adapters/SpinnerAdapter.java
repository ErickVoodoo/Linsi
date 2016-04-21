package com.linzon.ru.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.linzon.ru.R;

/**
 * Created by erick on 8.4.16.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int textViewResourceId, String[] itemList) {
        super(context, textViewResourceId);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_item, parent, false);
        row.findViewById(R.id.spinnerText);
        return row;
    }
}
