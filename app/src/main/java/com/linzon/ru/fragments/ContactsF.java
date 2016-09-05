package com.linzon.ru.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.linzon.ru.R;

/**
 * Created by Admin on 28.03.2016.
 */
public class ContactsF extends Fragment {
    View view;

    Button callMoscowButton;
    Button callSaintPetersburgButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.fragment_contacts, container, false);

        initButtons();
        return view;
    }

    private void initButtons() {
        callMoscowButton = (Button) view.findViewById(R.id.callMSButton);
        callSaintPetersburgButton = (Button) view.findViewById(R.id.callSpButton);

        callMoscowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ContactsF.this.getActivity().getResources().getString(R.string.contactPhoneMoscow).replaceAll("[^0-9.]", "")));
                startActivity(callIntent);
            }
        });

        callSaintPetersburgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ContactsF.this.getActivity().getResources().getString(R.string.contactPhoneSP).replaceAll("[^0-9.]", "")));
                startActivity(callIntent);
            }
        });
    }
}
