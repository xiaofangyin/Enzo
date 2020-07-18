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
package com.enzo.module_d.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.matisse.Matisse;
import com.enzo.commonlib.utils.matisse.MimeType;
import com.enzo.commonlib.utils.matisse.engine.impl.GlideEngine;
import com.enzo.commonlib.utils.matisse.filter.Filter;
import com.enzo.commonlib.utils.matisse.internal.entity.CaptureStrategy;
import com.enzo.commonlib.utils.matisse.listener.OnCheckedListener;
import com.enzo.commonlib.utils.matisse.listener.OnSelectedListener;
import com.enzo.module_d.R;
import com.enzo.module_d.ui.filter.GifSizeFilter;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.functions.Action1;

public class MDMatisseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int REQUEST_CODE_CHOOSE_SINGLE = 24;

    private ImageView imageView;
    private UriAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.zhihu).setOnClickListener(this);
        findViewById(R.id.dracula).setOnClickListener(this);
        findViewById(R.id.only_gif).setOnClickListener(this);
        findViewById(R.id.single).setOnClickListener(this);

        imageView = findViewById(R.id.single_choose_result);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new UriAdapter());

        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            ToastUtils.showToast("当前应用缺少读取sd卡权限");
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
        } else if (requestCode == REQUEST_CODE_CHOOSE_SINGLE && resultCode == RESULT_OK) {
            List<Uri> list = Matisse.obtainResult(data);
            if (list != null && !list.isEmpty()) {
                ImageLoader.Builder builder = new ImageLoader.Builder(this);
                builder.load(list.get(0))
                        .signature(String.valueOf(System.currentTimeMillis()))
                        .build()
                        .into(imageView);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.zhihu) {
            Matisse.from(MDMatisseActivity.this)
                    .choose(MimeType.ofImage(), false)
                    .theme(R.style.Matisse_Zhihu)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, "enzo"))
                    .maxSelectable(9)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .spanCount(4)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .setOnSelectedListener(new OnSelectedListener() {
                        @Override
                        public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                            Log.e("xfy", "onSelected: pathList=" + pathList);
                        }
                    })
                    .showSingleMediaType(true)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .autoHideToolbarOnSingleTap(true)
                    .setOnCheckedListener(new OnCheckedListener() {
                        @Override
                        public void onCheck(boolean isChecked) {
                            Log.e("xfy", "onCheck: isChecked=" + isChecked);
                        }
                    })
                    .forResult(REQUEST_CODE_CHOOSE);
        } else if (id == R.id.dracula) {
            Matisse.from(MDMatisseActivity.this)
                    .choose(MimeType.ofImage())
                    .theme(R.style.Matisse_Dracula)
                    .countable(false)
                    .showPreview(false)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .maxSelectable(9)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE);
        } else if (id == R.id.only_gif) {
            Matisse.from(MDMatisseActivity.this)
                    .choose(MimeType.of(MimeType.GIF), false)
                    .countable(true)
                    .maxSelectable(9)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .showSingleMediaType(true)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .autoHideToolbarOnSingleTap(true)
                    .forResult(REQUEST_CODE_CHOOSE);
        } else if (id == R.id.single) {
            Matisse.from(MDMatisseActivity.this)
                    .choose(MimeType.ofImage(), false)
                    .countable(true)
                    .singleChoose(true)
                    .crop(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, "enzo"))
                    .maxSelectable(9)
                    .spanCount(4)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .showSingleMediaType(true)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .autoHideToolbarOnSingleTap(true)
                    .forResult(REQUEST_CODE_CHOOSE_SINGLE);
        }
        mAdapter.setData(null, null);
    }

    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

        private List<Uri> mUris;
        private List<String> mPaths;

        void setData(List<Uri> uris, List<String> paths) {
            mUris = uris;
            mPaths = paths;
            notifyDataSetChanged();
        }

        @Override
        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UriViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.md_matisse_uri_item, parent, false));
        }

        @Override
        public void onBindViewHolder(UriViewHolder holder, int position) {
            holder.mUri.setText(mUris.get(position).toString());
            holder.mPath.setText(mPaths.get(position));

            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
            holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size();
        }

        static class UriViewHolder extends RecyclerView.ViewHolder {

            private TextView mUri;
            private TextView mPath;

            UriViewHolder(View contentView) {
                super(contentView);
                mUri = (TextView) contentView.findViewById(R.id.uri);
                mPath = (TextView) contentView.findViewById(R.id.path);
            }
        }
    }

}
