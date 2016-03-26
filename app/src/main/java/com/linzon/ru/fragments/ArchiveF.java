package com.linzon.ru.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.R;

/**
 * Created by Admin on 26.03.2016.
 */
public class ArchiveF extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_archive, container, false);
        return view;
    }
}
