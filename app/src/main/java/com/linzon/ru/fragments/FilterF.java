package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBHelper;

/**
 * Created by erick on 15.4.16.
 */
public class FilterF extends Fragment {
    View view;

    Spinner vendorSpinner;
    Spinner bcSpinner;
    Spinner pwrSpinner;
    Spinner colorSpinner;

    Button offerAddToChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_filters, container, false);

        initSpinner();
        initButtons();
        return view;
    }

    private void initSpinner() {
        vendorSpinner = (Spinner) view.findViewById(R.id.filterVENDORparam);
        bcSpinner = (Spinner) view.findViewById(R.id.filterBCparam);
        pwrSpinner = (Spinner) view.findViewById(R.id.filterPWRparam);
        colorSpinner = (Spinner) view.findViewById(R.id.filterCOLORparam);

        ArrayAdapter<String> adapterVENDOR = new ArrayAdapter<String>(FilterF.this.getActivity(), android.R.layout.simple_spinner_item, DBHelper.getInstance().getVendors());
        adapterVENDOR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendorSpinner.setAdapter(adapterVENDOR);
        vendorSpinner.setSelection(0);

        ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(FilterF.this.getActivity(), android.R.layout.simple_spinner_item, Constants.Filter_BC);
        adapterVENDOR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bcSpinner.setAdapter(adapterBC);
        bcSpinner.setSelection(0);

        ArrayAdapter<String> adapterPWR = new ArrayAdapter<String>(FilterF.this.getActivity(), android.R.layout.simple_spinner_item, Constants.Filter_PWR);
        adapterPWR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pwrSpinner.setAdapter(adapterPWR);
        pwrSpinner.setSelection(0);

        ArrayAdapter<String> adapterCOLOR = new ArrayAdapter<String>(FilterF.this.getActivity(), android.R.layout.simple_spinner_item, Constants.Filter_COLOR);
        adapterCOLOR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapterCOLOR);
        colorSpinner.setSelection(0);
    }

    private void initButtons() {
        offerAddToChart = (Button) view.findViewById(R.id.offerAddToChart);
        offerAddToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) FilterF.this.getActivity()).showCategory(-2,
                        new String[]{
                                null,
                                vendorSpinner.getSelectedItemPosition() == 0 ? null : vendorSpinner.getSelectedItem().toString(),
                                bcSpinner.getSelectedItemPosition() == 0 ? null : bcSpinner.getSelectedItem().toString(),
                                pwrSpinner.getSelectedItemPosition() == 0 ? null : pwrSpinner.getSelectedItem().toString(),
                                colorSpinner.getSelectedItemPosition() == 0 ? null : colorSpinner.getSelectedItem().toString()});
            }
        });
    }
}
