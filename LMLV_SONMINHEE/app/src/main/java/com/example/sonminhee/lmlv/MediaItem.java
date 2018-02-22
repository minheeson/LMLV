package com.example.sonminhee.lmlv;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by sonminhee on 2018. 2. 22..
 */

public class MediaItem {


    public long mId;
    public String mTitle;
    public String mDisplayname;
    public String mMimetype;
    public String mDate;
    public String mSize;

    public static MediaItem bindCursor(Cursor cursor) {
        Log.i("MediaItem", ":: count :::: " + cursor.getCount());
        Log.i("MediaItem", ":: position :::: " + cursor.getPosition());

        MediaItem mediaItem = new MediaItem();
        mediaItem.mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
        mediaItem.mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
        mediaItem.mMimetype = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        mediaItem.mDate = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED));
        mediaItem.mSize = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));



        return mediaItem;
    }
}
