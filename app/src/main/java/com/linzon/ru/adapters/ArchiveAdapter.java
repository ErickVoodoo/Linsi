package com.linzon.ru.adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linzon.ru.R;
import com.linzon.ru.api.ApiConnector;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.Values;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.CustomOfferData;
import com.linzon.ru.models.OOffer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(arrayList.get(position).getOrdered_at()) * 1000);

        holder.orderedAt.setText(this.activity.getResources().getString(R.string.static_ordered) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " + Constants.Month[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));

        holder.price.setText(this.activity.getResources().getString(R.string.static_price) + " " + String.valueOf(Math.abs(Integer.valueOf(arrayList.get(position).getPrice()) * Integer.valueOf(arrayList.get(position).getCount()))) + " " + this.activity.getResources().getString(R.string.static_exchange));
        holder.count.setText(this.activity.getResources().getString(R.string.static_count) + " " + arrayList.get(position).getCount());

        DBAsync.asyncGetOfferInfo(Integer.parseInt(arrayList.get(position).getOffer_id()), new DBAsync.CallbackGetOffer() {
            @Override
            public void onSuccess(OOffer success) {
                holder.name.setText(success.getName().replace("контактные", "").replace("линзы", "").replace("Контактные", "").trim());
                Picasso.with(activity)
                        .load(Constants.STATIC_SERVER + success.getPicture())
                        .into(holder.picture);
            }

            @Override
            public void onError(String error) {

            }
        });
        if(arrayList.get(position).getData() != null) {
            holder.archiveParams.setVisibility(View.VISIBLE);
            holder.archiveParams.setText(CustomOfferData.toCompactString(arrayList.get(position).getData()));
        } else {
            holder.archiveParams.setVisibility(View.GONE);
        }
        holder.archiveAddToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues order = DBHelper.setBasketContentValues(
                        arrayList.get(position).getOffer_id(),
                        null,
                        arrayList.get(position).getName(),
                        arrayList.get(position).getCount(),
                        arrayList.get(position).getPrice(),
                        arrayList.get(position).getData(),
                        Constants.STATUS_OPEN,
                        new Date().toString(),
                        "");
                DBHelper.getInstance().insertToBasket(order, arrayList.get(position).getCount());
                Intent addToBasket = new Intent();
                addToBasket.setAction(Constants.BROADCAST_ADD_TO_BASKET_FROM_ARCHIVE);
                LocalBroadcastManager.getInstance(activity).sendBroadcast(addToBasket);
                Values.showTopSnackBar(activity, "Товар добавлен в корзину", null, null, Snackbar.LENGTH_SHORT);
            }
        });

        if(isShowNew(arrayList.get(position))) {
            holder.orderId.setVisibility(View.VISIBLE);
            ApiConnector.asyncSimpleGetRequest(Constants.STATIC_ORDER_STATE + arrayList.get(position).getOrder_id(), new ApiConnector.CallbackString() {
                @Override
                public void onSuccess(String success) {
                    /*RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);

                    params.setMargins(0, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            8,
                            ArchiveAdapter.this.activity.getResources().getDisplayMetrics()
                    ), 0, 0);
                    holder.itemMainContainer.setLayoutParams(params);*/
                    if(success.equals("")) {
                        holder.orderId.setText("Заказ №" + arrayList.get(position).getOrder_id());
                        holder.marginTopArchive.setVisibility(View.VISIBLE);
                        return;
                    }
                    holder.orderId.setText("Заказ №" + arrayList.get(position).getOrder_id() + " "  + Constants.OrderStatuses[Integer.parseInt(success.replace("\"","")) - 1]);
                    holder.marginTopArchive.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            holder.marginTopArchive.setVisibility(View.GONE);
            holder.orderId.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View marginTopArchive;
        public CardView itemMainContainer;
        public TextView name;
        public ImageView picture;
        public TextView price;
        public TextView count;
        public TextView orderedAt;
        public TextView archiveParams;
        public TextView orderId;
        public Button archiveAddToBasket;

        public ViewHolder(View itemView) {
            super(itemView);
            marginTopArchive = (View) itemView.findViewById(R.id.marginTopArchive);
            itemMainContainer = (CardView) itemView.findViewById(R.id.itemArchiveContainer);
            name = (TextView) itemView.findViewById(R.id.archiveName);
            picture = (ImageView) itemView.findViewById(R.id.archivePicture);
            price = (TextView) itemView.findViewById(R.id.archivePrice);
            count = (TextView) itemView.findViewById(R.id.archiveCount);
            orderedAt = (TextView) itemView.findViewById(R.id.archiveOrderedAt);
            archiveParams = (TextView) itemView.findViewById(R.id.archiveParams);
            archiveAddToBasket = (Button) itemView.findViewById(R.id.archiveAddToBasket);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
        }
    }

    public ArchiveAdapter(Activity activity, ArrayList<BasketItem> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    int lastId = 0;

    public boolean isShowNew(BasketItem item) {
        lastId = Integer.parseInt(item.getOrder_id());

        ArrayList<BasketItem> newItems = new ArrayList<>();
        for(int i = 0;i < arrayList.size(); i++) {
            if(arrayList.get(i).getOrder_id().equals(item.getOrder_id())) {
                newItems.add(arrayList.get(i));
            }
        }

        return newItems.get(0).getId().equals(item.getId());
    }
}
