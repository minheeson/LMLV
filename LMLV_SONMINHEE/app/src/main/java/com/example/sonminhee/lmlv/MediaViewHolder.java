package com.example.sonminhee.lmlv;

import android.content.ContentUris;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by sonminhee on 2018. 2. 22..
 */

public class MediaViewHolder extends RecyclerView.ViewHolder {
    final static String TAG = "MediaViewHolder";

    private final Uri mediaUri = Uri.parse("content://media/external/images/media");
    private ImageView mImgMedia;
    private TextView mTxtTitle;
    private TextView mTxtRoute;
    private TextView mTxtDate;

    private MediaItem mItem;
    private int mPosition;

    public MediaViewHolder(View itemView) {
        super(itemView);
        mImgMedia = itemView.findViewById(R.id.img_media);
        mTxtTitle = itemView.findViewById(R.id.txt_title);
        mTxtRoute = itemView.findViewById(R.id.txt_route);
        mTxtDate = itemView.findViewById(R.id.txt_date);

    }

    public void setMediaItem(MediaItem item, int position) {
        //Log.i(TAG, " :: setMediaItem :: ");
        mItem = item;
        mPosition = position;
        String[] mimetype = item.mMimetype.split("/");
        mTxtTitle.setText(item.mTitle + "." + mimetype[1]);
        Uri imgUri = ContentUris.withAppendedId(mediaUri, item.mId);
        mTxtRoute.setText(imgUri.toString().substring(10));
        mTxtDate.setText(item.mDate);

        Glide.with(itemView).load(imgUri).apply(RequestOptions.circleCropTransform()).into(mImgMedia);
    }
}


