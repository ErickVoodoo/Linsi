package com.linzon.ru.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linzon.ru.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    /*ArrayList<PostModel> arrayList;
    private Activity activity;*/

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*Picasso.with(this.activity)
                .load(arrayList.get(position).getSearchModel().getPhoto_100())
                .transform(new CircleTransformation())
                .into(holder.postPhoto);

        Picasso.with(this.activity)
                .load(R.drawable.ic_favorite_white_24dp)
                .into(holder.likesPost, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.likesPost.getDrawable().setColorFilter(PostAdapter.this.activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    }

                    @Override
                    public void onError() {

                    }
                });

        Picasso.with(this.activity)
                .load(R.drawable.ic_call_split_white_24dp)
                .into(holder.repostsPost, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.repostsPost.getDrawable().setColorFilter(PostAdapter.this.activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    }

                    @Override
                    public void onError() {

                    }
                });

        Picasso.with(this.activity)
                .load(R.drawable.ic_chat_bubble_white_24dp)
                .into(holder.commentsPost, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.commentsPost.getDrawable().setColorFilter(PostAdapter.this.activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.postUserName.setText(arrayList.get(position).getSearchModel().getFirst_name() + " " + arrayList.get(position).getSearchModel().getLast_name());
        holder.postDate.setText(TimeCommon.getTimeFromUnix(Integer.parseInt(arrayList.get(position).getDate())));
        holder.postComments.setText(String.valueOf(arrayList.get(position).getComments().getCount()));
        holder.postLikes.setText(String.valueOf(arrayList.get(position).getLikes().getCount()));
        holder.postReposts.setText(String.valueOf(arrayList.get(position).getReposts().getCount()));

        CategoryAdapter adapterPhoto = new CategoryAdapter(arrayList.get(position).getPhotosFromAttachments(arrayList.get(position).getAttachments()), this.activity);
        if(adapterPhoto.getItemCount() != 0){
            LinearLayoutManager mLinearLayoutManagerPhoto = new CustomGridLayoutManager(this.activity.getApplicationContext(), 1);
            mLinearLayoutManagerPhoto.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.postAttachementsPhoto.setHasFixedSize(true);
            holder.postAttachementsPhoto.setLayoutManager(mLinearLayoutManagerPhoto);
            holder.postAttachementsPhoto.setVisibility(View.VISIBLE);
            holder.postAttachementsPhoto.setAdapter(adapterPhoto);
        } else
            holder.postAttachementsPhoto.setVisibility(View.GONE);

        VideosAdapter adapterVideo = new VideosAdapter(arrayList.get(position).getVideosFromAttachments(arrayList.get(position).getAttachments()), this.activity);
        if(adapterVideo.getItemCount() != 0){
            LinearLayoutManager mLinearLayoutManagerAudio = new CustomGridLayoutManager(this.activity.getApplicationContext(), 1);
            mLinearLayoutManagerAudio.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.postAttachementsVideo.setHasFixedSize(true);
            holder.postAttachementsVideo.setLayoutManager(mLinearLayoutManagerAudio);
            holder.postAttachementsVideo.setVisibility(View.VISIBLE);
            holder.postAttachementsVideo.setAdapter(adapterVideo);
        } else
            holder.postAttachementsVideo.setVisibility(View.GONE);

        DocsAdapter adapterDoc = new DocsAdapter(arrayList.get(position).getDocsFromAttachments(arrayList.get(position).getAttachments()), this.activity);
        if(adapterDoc.getItemCount() != 0){
            LinearLayoutManager mLinearLayoutManagerAudio = new CustomGridLayoutManager(this.activity.getApplicationContext(), 1);
            mLinearLayoutManagerAudio.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.postAttachementsDoc.setHasFixedSize(true);
            holder.postAttachementsDoc.setLayoutManager(mLinearLayoutManagerAudio);
            holder.postAttachementsDoc.setVisibility(View.VISIBLE);
            holder.postAttachementsDoc.setAdapter(adapterDoc);
        } else
            holder.postAttachementsDoc.setVisibility(View.GONE);

        AudioAdapter adapterAudio = new AudioAdapter(arrayList.get(position).getAudiosFromAttachments(arrayList.get(position).getAttachments()), this.activity);
        if(adapterAudio.getItemCount() != 0){
            LinearLayoutManager mLinearLayoutManagerAudio = new CustomGridLayoutManager(this.activity.getApplicationContext(), 1);
            mLinearLayoutManagerAudio.setOrientation(LinearLayoutManager.VERTICAL);
            holder.postAttachementsAudio.setHasFixedSize(true);
            holder.postAttachementsAudio.setLayoutManager(mLinearLayoutManagerAudio);
            holder.postAttachementsAudio.setVisibility(View.VISIBLE);
            holder.postAttachementsAudio.setAdapter(adapterAudio);
        } else
            holder.postAttachementsAudio.setVisibility(View.GONE);

        if(arrayList.get(position).getText().length() != 0){
            holder.postText.setVisibility(View.VISIBLE);
            String text = String.valueOf(arrayList.get(position).getText());
            // \[(.*?)]
            Pattern pattern = Pattern.compile("\\[.*?]");
            Matcher matcher = pattern.matcher(text);

            List<String> listMatches = new ArrayList<String>();
            while(matcher.find())
            {
                listMatches.add(matcher.group(0));
            }

            for(String s : listMatches)
            {
                String temp = s.substring(1,s.length() - 1);
                final String[] array = temp.replace("|", "-").split("-");
                final String id = "http://vk.com/" + array[0] + "|" + array[1];
                text = text.replace(s, id);

//                if(array[0].substring(0,2).equals("id")) {
//                    Intent intent = new Intent(PostAdapter.this.activity, UserProfile.class);
//                    intent.putExtra("uid", array[0].substring(2, array[0].length()));
//                    PostAdapter.this.activity.startActivity(intent);
//                }
            }

            holder.postText.setText(Html.fromHtml(text));
            holder.postText.setMovementMethod(SensibleLinkMovementMethod.getInstance());
        } else {
            holder.postText.setVisibility(View.GONE);
        }*/
    }

    static class SensibleLinkMovementMethod extends LinkMovementMethod {

        private boolean mLinkClicked;

        private String mClickedLink;

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

           /* if (action == MotionEvent.ACTION_UP) {
                mLinkClicked = false;
                mClickedLink = null;
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    SensibleUrlSpan span = (SensibleUrlSpan) link[0];
                    mLinkClicked = span.onClickSpan(widget);
                    mClickedLink = span.getURL();
                    return mLinkClicked;
                }
            }
            super.onTouchEvent(widget, buffer, event);*/

            return false;
        }

        public boolean isLinkClicked() {
            return mLinkClicked;
        }

        public String getClickedLink() {
            return mClickedLink;
        }

    }

    /*public void updatePosts(ArrayList<PostModel> adapters) {
        if(null != this.arrayList) {
            this.arrayList.addAll(adapters);
        }
        notifyItemInserted(this.arrayList.size());
    }*/

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView postPhoto;
        public ImageView commentsPost;
        public ImageView likesPost;
        public ImageView repostsPost;
        public TextView postUserName;
        public TextView postText;
        public TextView postDate;
        public TextView postComments;
        public TextView postLikes;
        public TextView postReposts;
        public RecyclerView postAttachementsPhoto;
        public RecyclerView postAttachementsAudio;
        public RecyclerView postAttachementsDoc;
        public RecyclerView postAttachementsVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            /*postPhoto = (ImageView) itemView.findViewById(R.id.postPhoto);
            likesPost = (ImageView) itemView.findViewById(R.id.likesPost);
            commentsPost = (ImageView) itemView.findViewById(R.id.commentsPost);
            repostsPost = (ImageView) itemView.findViewById(R.id.repostsPost);
            postUserName = (TextView) itemView.findViewById(R.id.postUserName);
            postDate = (TextView) itemView.findViewById(R.id.postDate);
            postComments = (TextView) itemView.findViewById(R.id.postComments);
            postLikes = (TextView) itemView.findViewById(R.id.postLikes);
            postReposts = (TextView) itemView.findViewById(R.id.postReposts);
            postText = (TextView) itemView.findViewById(R.id.postText);
            postAttachementsPhoto = (RecyclerView) itemView.findViewById(R.id.postAttachementsPhoto);
            postAttachementsAudio = (RecyclerView) itemView.findViewById(R.id.postAttachementsAudio);
            postAttachementsVideo = (RecyclerView) itemView.findViewById(R.id.postAttachementsVideo);
            postAttachementsDoc = (RecyclerView) itemView.findViewById(R.id.postAttachementsDoc);*/
        }
    }

    public PostAdapter(Activity activity){
        /*this.arrayList = arrayList;
        this.activity = activity;*/
    }
}