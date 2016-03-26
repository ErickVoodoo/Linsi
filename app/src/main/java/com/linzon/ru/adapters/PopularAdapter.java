package com.linzon.ru.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linzon.ru.Offer;
import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.POffer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<POffer> arrayList;
    private Activity activity;

    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(this.activity)
                .load(Constants.STATIC_SERVER + arrayList.get(position).getPicture())
                .into(holder.picture);

        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(this.activity.getResources().getString(R.string.static_price) + " " + arrayList.get(position).getPrice() + " " + this.activity.getResources().getString(R.string.static_exchange));
        holder.rate.setText(this.activity.getResources().getString(R.string.static_rate) + " " + arrayList.get(position).getRate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Offer.class);
                intent.putExtra("OFFER_ID", PopularAdapter.this.arrayList.get(position).getId());
                intent.putExtra("CATEGORY_ID", "0");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView picture;
        public TextView name;
        public TextView price;
        public TextView rate;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.popularCardView);
            picture = (ImageView) itemView.findViewById(R.id.popularPicture);
            name = (TextView) itemView.findViewById(R.id.popularName);
            rate = (TextView) itemView.findViewById(R.id.popularRate);
            price = (TextView) itemView.findViewById(R.id.popularPrice);
        }
    }

    public PopularAdapter(ArrayList<POffer> arrayList, Activity activity) {
        //this.width =  Values.GET_SCREEN_WIDTH(activity.getApplicationContext()) - Values.dpToPx(activity.getApplicationContext(), 8);
        this.arrayList = arrayList;
        this.activity = activity;
    }
}
