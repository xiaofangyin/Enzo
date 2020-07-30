/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.enzo.commonlib.utils.matisse.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.matisse.PhotoCropConfig;
import com.enzo.commonlib.utils.matisse.internal.entity.Album;
import com.enzo.commonlib.utils.matisse.internal.entity.Item;
import com.enzo.commonlib.utils.matisse.internal.entity.SelectionSpec;
import com.enzo.commonlib.utils.matisse.internal.model.AlbumCollection;
import com.enzo.commonlib.utils.matisse.internal.model.SelectedItemCollection;
import com.enzo.commonlib.utils.matisse.internal.ui.MediaSingleSelectionFragment;
import com.enzo.commonlib.utils.matisse.internal.ui.adapter.AlbumMediaSingleAdapter;
import com.enzo.commonlib.utils.matisse.internal.ui.adapter.AlbumsAdapter;
import com.enzo.commonlib.utils.matisse.internal.ui.widget.AlbumsSpinner;
import com.enzo.commonlib.utils.matisse.internal.utils.MediaStoreCompat;
import com.enzo.commonlib.utils.matisse.internal.utils.PathUtils;
import com.enzo.commonlib.utils.matisse.internal.utils.SingleMediaScanner;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class MatisseSingleActivity extends AppCompatActivity implements
        AlbumCollection.AlbumCallbacks, AdapterView.OnItemSelectedListener,
        AlbumMediaSingleAdapter.OnMediaClickListener, AlbumMediaSingleAdapter.OnPhotoCapture {

    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    public static final String EXTRA_RESULT_SELECTION_PATH = "extra_result_selection_path";
    private static final int REQUEST_CODE_CAPTURE = 24;
    private final AlbumCollection mAlbumCollection = new AlbumCollection();
    private MediaStoreCompat mMediaStoreCompat;
    private SelectedItemCollection mSelectedCollection = new SelectedItemCollection(this);
    private SelectionSpec mSpec;

    private AlbumsSpinner mAlbumsSpinner;
    private AlbumsAdapter mAlbumsAdapter;
    private View mContainer;
    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mSpec = SelectionSpec.getInstance();
        setTheme(mSpec.themeId);
        super.onCreate(savedInstanceState);
        if (!mSpec.hasInited) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        setContentView(R.layout.activity_matisse_single);

        if (mSpec.needOrientationRestriction()) {
            setRequestedOrientation(mSpec.orientation);
        }

        if (mSpec.capture) {
            mMediaStoreCompat = new MediaStoreCompat(this);
            if (mSpec.captureStrategy == null)
                throw new RuntimeException("Don't forget to set CaptureStrategy.");
            mMediaStoreCompat.setCaptureStrategy(mSpec.captureStrategy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        TypedArray ta = getTheme().obtainStyledAttributes(new int[]{R.attr.album_element_color});
        int color = ta.getColor(0, 0);
        ta.recycle();
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        mContainer = findViewById(R.id.container);
        mEmptyView = findViewById(R.id.empty_view);

        mSelectedCollection.onCreate(savedInstanceState);

        mAlbumsAdapter = new AlbumsAdapter(this, null, false);
        mAlbumsSpinner = new AlbumsSpinner(this);
        mAlbumsSpinner.setOnItemSelectedListener(this);
        mAlbumsSpinner.setSelectedTextView((TextView) findViewById(R.id.selected_album));
        mAlbumsSpinner.setPopupAnchorView(findViewById(R.id.toolbar));
        mAlbumsSpinner.setAdapter(mAlbumsAdapter);
        mAlbumCollection.onCreate(this, this);
        mAlbumCollection.onRestoreInstanceState(savedInstanceState);
        mAlbumCollection.loadAlbums();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSelectedCollection.onSaveInstanceState(outState);
        mAlbumCollection.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAlbumCollection.onDestroy();
        mSpec.onCheckedListener = null;
        mSpec.onSelectedListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_CAPTURE) {
            if (mSpec.crop) {
                Uri uri = Uri.fromFile(PhotoCropConfig.getAvatarCroppedFile(this));
                UCrop.of(mMediaStoreCompat.getCurrentPhotoUri(), uri)
                        .withOptions(PhotoCropConfig.getOptions())
                        .start(this);
            } else {
                Uri contentUri = mMediaStoreCompat.getCurrentPhotoUri();
                String path = mMediaStoreCompat.getCurrentPhotoPath();
                ArrayList<Uri> selected = new ArrayList<>();
                selected.add(contentUri);
                ArrayList<String> selectedPath = new ArrayList<>();
                selectedPath.add(path);
                Intent result = new Intent();
                result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selected);
                result.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, selectedPath);
                setResult(RESULT_OK, result);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    MatisseSingleActivity.this.revokeUriPermission(contentUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                new SingleMediaScanner(this.getApplicationContext(), path, new SingleMediaScanner.ScanListener() {
                    @Override
                    public void onScanFinish() {
                        Log.i("SingleMediaScanner", "scan finish!");
                    }
                });
                finish();
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            Intent result = new Intent();
            ArrayList<Uri> selectedUris = new ArrayList<>();
            ArrayList<String> selectedPaths = new ArrayList<>();
            selectedUris.add(UCrop.getOutput(data));
            selectedPaths.add(PathUtils.getPath(this, UCrop.getOutput(data)));
            result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedUris);
            result.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, selectedPaths);
            setResult(RESULT_OK, result);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAlbumCollection.setStateCurrentSelection(position);
        mAlbumsAdapter.getCursor().moveToPosition(position);
        Album album = Album.valueOf(mAlbumsAdapter.getCursor());
        if (album.isAll() && SelectionSpec.getInstance().capture) {
            album.addCaptureCount();
        }
        onAlbumSelected(album);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAlbumLoad(final Cursor cursor) {
        mAlbumsAdapter.swapCursor(cursor);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                cursor.moveToPosition(mAlbumCollection.getCurrentSelection());
                mAlbumsSpinner.setSelection(MatisseSingleActivity.this,
                        mAlbumCollection.getCurrentSelection());
                Album album = Album.valueOf(cursor);
                if (album.isAll() && SelectionSpec.getInstance().capture) {
                    album.addCaptureCount();
                }
                onAlbumSelected(album);
            }
        });
    }

    @Override
    public void onAlbumReset() {
        mAlbumsAdapter.swapCursor(null);
    }

    private void onAlbumSelected(Album album) {
        if (album.isAll() && album.isEmpty()) {
            mContainer.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mContainer.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            Fragment fragment = MediaSingleSelectionFragment.newInstance(album);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, MediaSingleSelectionFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onMediaClick(Album album, Item item, int adapterPosition) {
        if (mSpec.crop) {
            Uri sourceUri = Uri.fromFile(new File(Objects.requireNonNull(PathUtils.getPath(this, item.uri))));
            Uri uri = Uri.fromFile(PhotoCropConfig.getAvatarCroppedFile(this));
            UCrop.of(sourceUri, uri)
                    .withOptions(PhotoCropConfig.getOptions())
                    .start(this);
        } else {
            Intent result = new Intent();
            ArrayList<Uri> selectedUris = new ArrayList<>();
            ArrayList<String> selectedPaths = new ArrayList<>();
            selectedUris.add(item.getContentUri());
            selectedPaths.add(PathUtils.getPath(this, item.getContentUri()));
            result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedUris);
            result.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, selectedPaths);
            setResult(RESULT_OK, result);
            finish();
        }
    }

    @Override
    public void capture() {
        if (mMediaStoreCompat != null) {
            mMediaStoreCompat.dispatchCaptureIntent(this, REQUEST_CODE_CAPTURE);
        }
    }
}
