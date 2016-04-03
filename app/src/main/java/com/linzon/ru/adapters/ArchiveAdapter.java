package com.linzon.ru.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by erick on 29.3.16.
 */
public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ViewHolder> {

    Activity activity;
    ArrayList<BasketItem> arrayList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_archive, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("TIME", arrayList.get(position).getOrdered_at());
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(arrayList.get(position).getOrdered_at());
            cal.setTime(date);
            holder.orderedAt.setText(this.activity.getResources().getString(R.string.static_ordered) + " " +  cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.price.setText(this.activity.getResources().getString(R.string.static_price) + " " + String.valueOf(Math.abs(Integer.valueOf(arrayList.get(position).getPrice()) * Integer.valueOf(arrayList.get(position).getCount()))) + " " + this.activity.getResources().getString(R.string.static_exchange));
        holder.count.setText(this.activity.getResources().getString(R.string.static_count) + " " + arrayList.get(position).getCount());

        DBAsync.asyncGetOfferInfo(Integer.parseInt(arrayList.get(position).getOffer_id()), new DBAsync.CallbackGetOffer() {
            @Override
            public void onSuccess(OOffer success) {
                holder.name.setText(success.getName());
                Picasso.with(activity)
                        .load(Constants.STATIC_SERVER + success.getPicture())
                        .into(holder.picture);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView picture;
        public TextView price;
        public TextView count;
        public TextView orderedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.archiveName);
            picture = (ImageView) itemView.findViewById(R.id.archivePicture);
            price = (TextView) itemView.findViewById(R.id.archivePrice);
            count = (TextView) itemView.findViewById(R.id.archiveCount);
            orderedAt = (TextView) itemView.findViewById(R.id.archiveOrderedAt);
        }
    }

    public ArchiveAdapter(Activity activity, ArrayList<BasketItem> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }
}