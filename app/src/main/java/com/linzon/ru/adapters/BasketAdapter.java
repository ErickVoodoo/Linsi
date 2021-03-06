package com.linzon.ru.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.CustomOfferData;
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
        holder.price.setText(this.activity.getResources().getString(R.string.static_price) + " " + String.valueOf(Math.abs(Integer.valueOf(arrayList.get(position).getPrice()) * Integer.valueOf(arrayList.get(position).getCount()))) + " " + this.activity.getResources().getString(R.string.static_exchange));
        holder.count.setText(arrayList.get(position).getCount());
        holder.plusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countInt = Integer.parseInt(holder.count.getText().toString()) + 1;
                Intent intent = new Intent();
                intent.setAction(Constants.BROADCAST_UPDATE_COUNT);
                intent.putExtra("ID", arrayList.get(position).getId());
                intent.putExtra("COUNT", String.valueOf(countInt));
                LocalBroadcastManager.getInstance(BasketAdapter.this.activity).sendBroadcast(intent);
                holder.count.setText(String.valueOf(countInt));
                holder.price.setText(BasketAdapter.this.activity.getResources().getString(R.string.static_price) + " "
                        + (countInt != 0 ? String.valueOf(Math.abs(Integer.parseInt(arrayList.get(position).getPrice()) * countInt)) : "0") + " " + BasketAdapter.this.activity.getResources().getString(R.string.static_exchange));
            }
        });

        holder.minusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.count.getText().toString()) - 1 > 0) {
                    int countInt = Integer.parseInt(holder.count.getText().toString()) - 1;
                    Intent intent = new Intent();
                    intent.setAction(Constants.BROADCAST_UPDATE_COUNT);
                    intent.putExtra("ID", arrayList.get(position).getId());
                    intent.putExtra("COUNT", String.valueOf(countInt));
                    LocalBroadcastManager.getInstance(BasketAdapter.this.activity).sendBroadcast(intent);
                    holder.count.setText(String.valueOf(countInt));
                    holder.price.setText(BasketAdapter.this.activity.getResources().getString(R.string.static_price) + " "
                            + (countInt != 0 ? String.valueOf(Math.abs(Integer.parseInt(arrayList.get(position).getPrice()) * countInt)) : "0") + " " + BasketAdapter.this.activity.getResources().getString(R.string.static_exchange));
                }
            }
        });
        DBAsync.asyncGetOfferInfo(Integer.parseInt(arrayList.get(position).getOffer_id()), new DBAsync.CallbackGetOffer() {
            @Override
            public void onSuccess(OOffer success) {
                holder.basketName.setText(success.getName().replace("контактные", "").replace("линзы", "").replace("Контактные", "").trim());
                Picasso.with(activity)
                        .load(Constants.STATIC_SERVER + success.getPicture())
                        .into(holder.picture);
            }

            @Override
            public void onError(String error) {

            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                if(position != arrayList.size()) {
                    builder.setMessage(arrayList.get(position).getName())
                            .setTitle("Удалить этот продукт?")
                            .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBHelper.deleteFromBasket(arrayList.get(position).getId());
                                    arrayList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());

                                    Intent updatePrice = new Intent();
                                    updatePrice.setAction(Constants.BROADCAST_UPDATE_PRICE);
                                    LocalBroadcastManager.getInstance(activity).sendBroadcast(updatePrice);
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });
        if (arrayList.get(position).getData() != null)
            holder.basketParams.setText(CustomOfferData.toCompactString(arrayList.get(position).getData()));
        else
            holder.basketParams.setText("");
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
        public TextView basketParams;

        public Button plusCount;
        public Button minusCount;
        public Button removeItem;

        public ViewHolder(View itemView) {
            super(itemView);
            basketName = (TextView) itemView.findViewById(R.id.basketName);
            picture = (ImageView) itemView.findViewById(R.id.basketPicture);
            price = (TextView) itemView.findViewById(R.id.basketPrice);
            count = (TextView) itemView.findViewById(R.id.basketCount);
            basketParams = (TextView) itemView.findViewById(R.id.basketParams);

            plusCount = (Button) itemView.findViewById(R.id.basketItemPlusCount);
            minusCount = (Button) itemView.findViewById(R.id.basketItemMinusCount);
            removeItem = (Button) itemView.findViewById(R.id.basketItemRemoveItem);
        }
    }

    public BasketAdapter(Activity activity, ArrayList<BasketItem> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }
}
