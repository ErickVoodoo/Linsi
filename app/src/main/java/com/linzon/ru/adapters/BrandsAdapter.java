package com.linzon.ru.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linzon.ru.MainActivity;
import com.linzon.ru.R;

/**
 * Created by erick on 8.4.16.
 */
public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder> {
    private Activity activity;
    private String[] itemList;

    public BrandsAdapter(Activity activity, String[] itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public BrandsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false));
    }

    @Override
    public void onBindViewHolder(BrandsAdapter.ViewHolder holder, final int position) {
        holder.brand.setText(itemList[position]);
        holder.brandLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("BRAND", itemList[position]);
                ((MainActivity) BrandsAdapter.this.activity).showCategory(-2, new String[]{itemList[position], null, null, null, null});
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView brand;
        public RelativeLayout brandLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            brand = (TextView) itemView.findViewById(R.id.brandTextView);
            brandLinearLayout = (RelativeLayout) itemView.findViewById(R.id.brandLinearLayout);
        }
    }
}
