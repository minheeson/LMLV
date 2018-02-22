package com.example.sonminhee.lmlv;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sonminhee on 2018. 2. 22..
 */

public class MediaAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder>{



    final static String TAG = "MediaAdapter";

    ArrayList<MediaItem> mediaList = new ArrayList<MediaItem>();

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public MediaAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        Log.i(TAG, "count :: " + cursor.getCount());
        Log.i(TAG, "cursor position ::: " + cursor.getPosition());
        MediaItem mediaItem = MediaItem.bindCursor(cursor);
        ((MediaViewHolder) viewHolder).setMediaItem(mediaItem, cursor.getPosition());


//        if (mediaList.get(cursor.getPosition()) != null) {
//            mediaList.get(cursor.getPosition());
//        } else {
//            mediaItem = MediaItem.bindCursor(cursor);
//            ((MediaViewHolder) viewHolder).setMediaItem(mediaItem, cursor.getPosition());
//        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_media, parent, false);
        return new MediaViewHolder(view);
    }

    public void setMediaList(Cursor cursor) {
        Log.i(TAG, "cursor count ::: " + cursor.getCount());
        mediaList = new ArrayList<>(cursor.getCount());
    }


}
