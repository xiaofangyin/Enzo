package com.enzo.commonlib.widget.overscroll;

import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.widget.overscroll.adapters.AbsListViewOverScrollDecorAdapter;
import com.enzo.commonlib.widget.overscroll.adapters.HorizontalScrollViewOverScrollDecorAdapter;
import com.enzo.commonlib.widget.overscroll.adapters.RecyclerViewOverScrollDecorAdapter;
import com.enzo.commonlib.widget.overscroll.adapters.ScrollViewOverScrollDecorAdapter;
import com.enzo.commonlib.widget.overscroll.adapters.StaticOverScrollDecorAdapter;

public class OverScrollDecoratorHelper {

    public static final int ORIENTATION_VERTICAL = 0;
    public static final int ORIENTATION_HORIZONTAL = 1;

    public static void setUpOverScroll(RecyclerView recyclerView, int orientation) {
        switch (orientation) {
            case ORIENTATION_HORIZONTAL:
                new HorizontalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(recyclerView));
                break;
            case ORIENTATION_VERTICAL:
                new VerticalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(recyclerView));
                break;
            default:
                throw new IllegalArgumentException("orientation");
        }
    }

    public static void setUpOverScroll(ListView listView) {
        new VerticalOverScrollBounceEffectDecorator(new AbsListViewOverScrollDecorAdapter(listView));
    }

    public static void setUpOverScroll(GridView gridView) {
        new VerticalOverScrollBounceEffectDecorator(new AbsListViewOverScrollDecorAdapter(gridView));
    }

    public static void setUpOverScroll(ScrollView scrollView) {
        new VerticalOverScrollBounceEffectDecorator(new ScrollViewOverScrollDecorAdapter(scrollView));
    }

    public static void setUpOverScroll(HorizontalScrollView scrollView) {
        new HorizontalOverScrollBounceEffectDecorator(new HorizontalScrollViewOverScrollDecorAdapter(scrollView));
    }

    public static void setUpStaticOverScroll(View view, int orientation) {
        switch (orientation) {
            case ORIENTATION_HORIZONTAL:
                new HorizontalOverScrollBounceEffectDecorator(new StaticOverScrollDecorAdapter(view));
                break;

            case ORIENTATION_VERTICAL:
                new VerticalOverScrollBounceEffectDecorator(new StaticOverScrollDecorAdapter(view));
                break;

            default:
                new IllegalArgumentException("orientation");
        }
    }
}
