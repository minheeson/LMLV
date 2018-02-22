package com.example.sonminhee.lmlv;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int LOADER_ID = 1;
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private MediaAdapter mAdapter;

    int firstVisibleItem, visibleItemCount, totalItemCount;
    private CursorLoader cursorLoader;
    int existingImageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAuthority();
        init();
    }


    public void checkAuthority() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                getMediaList();
            }
        } else {
            getMediaList();
        }
    }


    private void init() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new MediaAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        // not scrolling
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        // scrolling
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        // scroll setting
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                cursorLoader.setSortOrder(MediaStore.Files.FileColumns.TITLE + " ASC LIMIT 30 OFFSET " + firstVisibleItem);
                mAdapter.changeCursor(cursorLoader.loadInBackground());

            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getMediaList();
        }
    }

    public void getMediaList() {
        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {


            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Uri uri = MediaStore.Files.getContentUri("external");

                String[] projection = new String[]{
                        MediaStore.Files.FileColumns._ID,
                        MediaStore.Files.FileColumns.TITLE,
                        MediaStore.Files.FileColumns.MIME_TYPE,
                        MediaStore.Files.FileColumns.DATE_ADDED,
                        MediaStore.Files.FileColumns.SIZE};

                String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ?";
                String[] selectionArgs = new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpeg")};

                final String[] imageCountProjection = new String[]{
                        "count(" + MediaStore.Images.ImageColumns._ID + ")",
                };

                Cursor countCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageCountProjection,
                        null,
                        null,
                        null);

                countCursor.moveToFirst();
                existingImageCount = countCursor.getInt(0);
                //Log.i("TEST", "TETETE ::: " + existingImageCount);

                cursorLoader = new CursorLoader(getApplicationContext(), uri, projection, selection, selectionArgs, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

                if (data != null && data.getCount() > 0) {
                    while (data.moveToNext()) {
                        Log.i(TAG, "Title:" + data.getString(data.getColumnIndex(MediaStore.Files.FileColumns.TITLE)));
                    }
                }

                ArrayList<Integer> mediaList = new ArrayList<>(existingImageCount);
                mAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);

            }
        });
    }

}
