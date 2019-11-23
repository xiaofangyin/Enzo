/*
 * Copyright (C) 2016 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.enzo.commonlib.widget.pdf.library;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;

import com.enzo.commonlib.net.download.DownloadUtil;
import com.enzo.commonlib.widget.pdf.library.util.FileUtil;

import java.io.File;

public class RemotePDFViewPager extends ViewPager {

    private String pdfUrl;

    public RemotePDFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUrl(String url) {
        pdfUrl = url;
    }

    public void start(final OnDownloadListener listener) {
        DownloadUtil.get().download(pdfUrl, getContext().getCacheDir().getAbsolutePath(), FileUtil.extractFileNameFromURL(pdfUrl), new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadStart() {
                if (listener != null) {
                    listener.onDownloadStart();
                }
            }

            @Override
            public void onDownloadSuccess(File file) {
                if (listener != null) {
                    listener.onDownloadSuccess(file);
                }
            }

            @Override
            public void onDownloading(int progress) {
                if (listener != null) {
                    listener.onDownloading(progress);
                }
            }

            @Override
            public void onDownloadFailed() {
                if (listener != null) {
                    listener.onDownloadFailed();
                }
            }
        });
    }

    public interface OnDownloadListener {
        void onDownloadStart();

        void onDownloadSuccess(File file);

        void onDownloading(int progress);

        void onDownloadFailed();
    }
}
