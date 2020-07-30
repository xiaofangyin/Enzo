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
package com.enzo.commonlib.utils.matisse.internal.utils;

import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


final class ExifInterfaceCompat {
    private static final String TAG = ExifInterfaceCompat.class.getSimpleName();
    private static final int EXIF_DEGREE_FALLBACK_VALUE = -1;

    private ExifInterfaceCompat() {

    }

    public static ExifInterface newInstance(String filename) throws IOException {
        if (filename == null) throw new NullPointerException("filename should not be null");
        return new ExifInterface(filename);
    }

    private static Date getExifDateTime(String filepath) {
        ExifInterface exif;
        try {
            exif = newInstance(filepath);
        } catch (IOException ex) {
            Log.e(TAG, "cannot read exif", ex);
            return null;
        }

        String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            return formatter.parse(date);
        } catch (ParseException e) {
            Log.d(TAG, "failed to parse date taken", e);
        }
        return null;
    }

    public static long getExifDateTimeInMillis(String filepath) {
        Date datetime = getExifDateTime(filepath);
        if (datetime == null) {
            return -1;
        }
        return datetime.getTime();
    }

    public static int getExifOrientation(String filepath) {
        ExifInterface exif;
        try {
            exif = newInstance(filepath);
        } catch (IOException ex) {
            Log.e(TAG, "cannot read exif", ex);
            return EXIF_DEGREE_FALLBACK_VALUE;
        }

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, EXIF_DEGREE_FALLBACK_VALUE);
        if (orientation == EXIF_DEGREE_FALLBACK_VALUE) {
            return 0;
        }
        // We only recognize a subset of orientation tag values.
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }
}