package com.linzon.ru.Activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linzon.ru.Activity.common.Values;
import com.linzon.ru.Activity.models.PostModel.Attachments.DocumentModel;
import com.linzon.ru.Activity.models.PostModel.Attachments.PhotoModel;
import com.linzon.ru.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by erick on 20.10.15.
 */
public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.ViewHolder> {
    ArrayList<DocumentModel> arrayList;
    private Activity activity;
    int width;

    @Override
    public DocsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.attachments_docs, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams params = holder.image.getLayoutParams();
        params.width = width;
        holder.image.setLayoutParams(params);

        if(this.arrayList.get(position).getExt().equals("gif")) {
            holder.docFileLayout.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(this.activity)
                    .load(arrayList.get(position).getThumb())
                    .into(holder.image);
        } else {
            holder.docFileLayout.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
            if(arrayList.get(position).getUrl().length() != 0)
                holder.docFileTextView.setText(arrayList.get(position).getTitle());
        }

        if(arrayList.get(position).getUrl().length() != 0) {
            holder.mainDocContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DocsAdapter.this.arrayList.get(position).getUrl()));
                    DocsAdapter.this.activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mainDocContainer;
        public ImageView image;
        public LinearLayout docFileLayout;
        public TextView docFileTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainDocContainer = (RelativeLayout) itemView.findViewById(R.id.mainDocContainer);
            image = (ImageView) itemView.findViewById(R.id.attachmentDocs);
            docFileLayout = (LinearLayout) itemView.findViewById(R.id.docFileLayout);
            docFileTextView = (TextView) itemView.findViewById(R.id.docFileTextView);
        }
    }

    public DocsAdapter(ArrayList<DocumentModel> arrayList, Activity activity) {
        this.width =  Values.GET_SCREEN_WIDTH(activity.getApplicationContext()) - Values.dpToPx(activity.getApplicationContext(), 8);
        this.arrayList = arrayList;
        this.activity = activity;
    }
}
