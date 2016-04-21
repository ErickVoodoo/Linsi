package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.Values;
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

        ArrayAdapter<String> adapterVENDOR = new ArrayAdapter<String>(FilterF.this.getActivity(), R.layout.spinner_center_item, DBHelper.getInstance().getVendors());
        vendorSpinner.setAdapter(adapterVENDOR);
        vendorSpinner.setSelection(0);

        ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(FilterF.this.getActivity(), R.layout.spinner_center_item, Constants.Filter_BC);
        bcSpinner.setAdapter(adapterBC);
        bcSpinner.setSelection(0);

        ArrayAdapter<String> adapterPWR = new ArrayAdapter<String>(FilterF.this.getActivity(), R.layout.spinner_center_item, Constants.Filter_PWR);
        pwrSpinner.setAdapter(adapterPWR);
        pwrSpinner.setSelection(0);

        ArrayAdapter<String> adapterCOLOR = new ArrayAdapter<String>(FilterF.this.getActivity(), R.layout.spinner_center_item, Constants.Filter_COLOR);
        colorSpinner.setAdapter(adapterCOLOR);
        colorSpinner.setSelection(0);
    }

    private void initButtons() {
        offerAddToChart = (Button) view.findViewById(R.id.offerAddToChart);
        offerAddToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vendorSpinner.getSelectedItemPosition() == 0 &&
                        bcSpinner.getSelectedItemPosition() == 0 &&
                        pwrSpinner.getSelectedItemPosition() == 0 &&
                        colorSpinner.getSelectedItemPosition() == 0) {
                    Values.showTopSnackBar(FilterF.this.getActivity(), "Выберите хотя бы 1 параметр для поиска", null, null, Snackbar.LENGTH_SHORT);
                    return;
                }
                ((MainActivity) FilterF.this.getActivity()).showCategory(-2,
                        null,
                        vendorSpinner.getSelectedItemPosition() == 0 ? null : vendorSpinner.getSelectedItem().toString(),
                        bcSpinner.getSelectedItemPosition() == 0 ? null : bcSpinner.getSelectedItem().toString(),
                        pwrSpinner.getSelectedItemPosition() == 0 ? null : pwrSpinner.getSelectedItem().toString(),
                        colorSpinner.getSelectedItemPosition() == 0 ? null : colorSpinner.getSelectedItem().toString());
            }
        });
    }
}
