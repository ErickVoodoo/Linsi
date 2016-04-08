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
    private Context context;
    private String[] itemList;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int textViewResourceId, String[] itemList) {
        super(context, textViewResourceId);
        this.context = context;
        this.itemList = itemList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = inflater.inflate(R.layout.spinner_item, parent, false);
//        TextView textView = (TextView) row.findViewById(R.id.spinnerText);
//        textView.setText(itemList[position]);
//        return row;
//    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_item, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.spinnerText);
//        textView.setText(itemList[position]);

        return row;
    }
}
