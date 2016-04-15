package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.R;

/**
 * Created by erick on 15.4.16.
 */
public class FilterF extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_filters, container, false);
        return view;
    }
}
