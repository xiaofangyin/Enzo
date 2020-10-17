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
package com.enzo.commonlib.utils.matisse.filter;

import android.content.Context;

import com.enzo.commonlib.utils.matisse.MimeType;
import com.enzo.commonlib.utils.matisse.internal.entity.IncapableCause;
import com.enzo.commonlib.utils.matisse.internal.entity.Item;

import java.util.Set;

@SuppressWarnings("unused")
public abstract class Filter {

    public static final int MIN = 0;

    public static final int MAX = Integer.MAX_VALUE;

    public static final int K = 1024;

    protected abstract Set<MimeType> constraintTypes();

    public abstract IncapableCause filter(Context context, Item item);

    protected boolean needFiltering(Context context, Item item) {
        for (MimeType type : constraintTypes()) {
            if (type.checkType(context.getContentResolver(), item.getContentUri())) {
                return true;
            }
        }
        return false;
    }
}
