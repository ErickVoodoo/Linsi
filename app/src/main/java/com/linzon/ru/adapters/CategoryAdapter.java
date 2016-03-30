package com.linzon.ru.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linzon.ru.Offer;
import com.linzon.ru.R;
import com.linzon.ru.common.Constants;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<OOffer> arrayList;
    private Activity activity;
    private double width;

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(arrayList.get(position).getVendor() == null)
            holder.vendor.setVisibility(View.GONE);
        else
            holder.vendor.setVisibility(View.VISIBLE);

        if(arrayList.get(position).getPrice() == null)
            holder.price.setVisibility(View.GONE);
        else
            holder.price.setVisibility(View.VISIBLE);

        Picasso.with(this.activity)
                .load(Constants.STATIC_SERVER + arrayList.get(position).getPicture())
                .into(holder.picture);

        /*ViewGroup.LayoutParams params = holder.picture.getLayoutParams();
        params.height = (int) this.width / 2;
        holder.picture.setLayoutParams(params);*/

        holder.description.setText(arrayList.get(position).getDescription());
        holder.name.setText(arrayList.get(position).getName());
        holder.vendor.setText(this.activity.getResources().getString(R.string.static_vendor) + " " + arrayList.get(position).getVendor());
        holder.price.setText(this.activity.getResources().getString(R.string.static_price) + " " + arrayList.get(position).getPrice() + " " + this.activity.getResources().getString(R.string.static_exchange));

        holder.itemMainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Offer.class);
                intent.putExtra("OFFER_ID", CategoryAdapter.this.arrayList.get(position).getId());
                intent.putExtra("CATEGORY_ID", CategoryAdapter.this.arrayList.get(position).getCategoryId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView itemMainContainer;
        public ImageView picture;
        public TextView name;
        public TextView vendor;
        public TextView price;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            itemMainContainer = (CardView) itemView.findViewById(R.id.itemMainContainer);
            picture = (ImageView) itemView.findViewById(R.id.categoryPicture);
            name = (TextView) itemView.findViewById(R.id.categoryName);
            vendor = (TextView) itemView.findViewById(R.id.categoryVendor);
            price = (TextView) itemView.findViewById(R.id.categoryPrice);
            description = (TextView) itemView.findViewById(R.id.categoryDescription);
        }
    }

    public CategoryAdapter(ArrayList<OOffer> arrayList, Activity activity) {
        //this.width =  Values.GET_SCREEN_WIDTH(activity.getApplicationContext()) - Values.dpToPx(activity.getApplicationContext(), 8);
        this.arrayList = arrayList;
        this.activity = activity;
    }
}
