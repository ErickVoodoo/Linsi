package com.linzon.ru.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linzon.ru.R;
import com.linzon.ru.models.BasketItem;

import java.util.ArrayList;

/**
 * Created by erick on 29.3.16.
 */
public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    Activity activity;
    ArrayList<BasketItem> arrayList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.basketName.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView basketName;

        public ViewHolder(View itemView) {
            super(itemView);
            basketName = (TextView) itemView.findViewById(R.id.basketName);
        }
    }

    public BasketAdapter(Activity activity, ArrayList<BasketItem> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }
}
