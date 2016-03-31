package com.linzon.ru.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.price.setText(this.activity.getResources().getString(R.string.static_price) + " " + arrayList.get(position).getPrice() + " " + this.activity.getResources().getString(R.string.static_exchange));
        holder.count.setText(arrayList.get(position).getCount());
        holder.plusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Constants.BROADCAST_UPDATE_COUNT);
                intent.putExtra("ID", arrayList.get(position).getId());
                intent.putExtra("COUNT", "+1");
                BasketAdapter.this.activity.sendBroadcast(intent);
                holder.count.setText(String.valueOf(Integer.parseInt(holder.count.getText().toString()) + 1));
            }
        });

        holder.minusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Constants.BROADCAST_UPDATE_COUNT);
                intent.putExtra("ID", arrayList.get(position).getId());
                intent.putExtra("COUNT", "-1");
                BasketAdapter.this.activity.sendBroadcast(intent);
                holder.count.setText(String.valueOf(Integer.parseInt(holder.count.getText().toString()) - 1));
            }
        });
        DBAsync.asyncGetOfferInfo(Integer.parseInt(arrayList.get(position).getOffer_id()), new DBAsync.CallbackGetOffer() {
            @Override
            public void onSuccess(OOffer success) {
                holder.basketName.setText(success.getName());
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
        public TextView basketName;
        public ImageView picture;
        public TextView price;
        public TextView count;

        public Button plusCount;
        public Button minusCount;

        public ViewHolder(View itemView) {
            super(itemView);
            basketName = (TextView) itemView.findViewById(R.id.basketName);
            picture = (ImageView) itemView.findViewById(R.id.basketPicture);
            price = (TextView) itemView.findViewById(R.id.basketPrice);
            count = (TextView) itemView.findViewById(R.id.basketCount);

            plusCount = (Button) itemView.findViewById(R.id.basketItemPlusCount);
            minusCount = (Button) itemView.findViewById(R.id.basketItemMinusCount);
        }
    }

    public BasketAdapter(Activity activity, ArrayList<BasketItem> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }
}
