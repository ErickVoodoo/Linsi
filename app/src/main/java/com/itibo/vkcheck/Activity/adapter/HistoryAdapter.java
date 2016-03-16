package com.itibo.vkcheck.Activity.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itibo.vkcheck.Activity.common.TimeCommon;
import com.itibo.vkcheck.Activity.models.PostModel.HistoryModel;
import com.itibo.vkcheck.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    ArrayList<HistoryModel> arrayList;
    Activity activity;

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(arrayList.get(position).getType() == 0) {
            holder.online.setBackgroundColor(this.activity.getResources().getColor(R.color.redColor));
        } else {
            holder.online.setBackgroundColor(this.activity.getResources().getColor(R.color.greenColor));
        }
        holder.online.setText(arrayList.get(position).getType() == 0 ? "Оффлайн " + (arrayList.size() > position + 1 ? "(в сети " + TimeCommon.getHourMinuteFromUnix(arrayList.get(position).getTime() - arrayList.get(position + 1) .getTime())  + ")": "") : "Онлайн");
        holder.text.setText(TimeCommon.getTimeFromUnix(arrayList.get(position).getTime()));
        holder.image.setImageResource(arrayList.get(position).getDevice() == 7 ? R.drawable.pc : R.drawable.phone);

        Log.e("Target", String.valueOf(arrayList.get(position).getTime()));
        Log.e("TEST", arrayList.get(position).getType() == 0 ? "Оффлайн " + (arrayList.size() > position + 1 ? "(в сети " + TimeCommon.getHourMinuteFromUnix(arrayList.get(position).getTime() - arrayList.get(position + 1) .getTime())  + ")": "") : "Онлайн " + arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView online;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.onlinePhoto);
            online = (TextView) itemView.findViewById(R.id.online);
            text = (TextView) itemView.findViewById(R.id.onlineTime);
        }
    }

    public HistoryAdapter(ArrayList<HistoryModel> arrayList, Activity activity){
        this.arrayList = arrayList;
        this.activity = activity;
    }
}