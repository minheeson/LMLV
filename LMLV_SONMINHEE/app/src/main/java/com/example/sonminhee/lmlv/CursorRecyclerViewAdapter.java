package com.example.sonminhee.lmlv;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by sonminhee on 2018. 2. 22..
 */

public class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private Context mContext;
    private Cursor mCursor;
    private boolean mDataValid;
    private int mRowIdColumn;
    private DataSetObserver mDataSetObserver;

    private static String TAG = "CursorRecyclerViewAdapter";


    public CursorRecyclerViewAdapter(Context context, Cursor cursor) {
        Log.i(TAG, ":: CursorRecyclerViewAdapter ::");
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(TAG, ":: onCreateViewHolder ::");
        return null;
    }

    public void onBindViewHolder(VH viewHolder, Cursor cursor) {
        Log.i(TAG, ":: onBindViewHolder ::");
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Log.i(TAG, ":: onBindViewHolder ::");
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        onBindViewHolder(holder, mCursor);

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, ":: getItemCount ::");
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, ":: getItemId ::");
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        Log.i(TAG, ":: swapCursor ::");
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            Log.i(TAG, "NotifyingDataSetObserver :: onChanged ::");
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            Log.i(TAG, "NotifyingDataSetObserver :: onInvalidated ::");
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
        }
    }


}
