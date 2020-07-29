package com.enzo.commonlib.widget.flowlayout;

import android.database.Observable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FlowLayoutAdapter<T> implements BaseFlowAdapter<T> {

    private final AdapterDataObservable mObservable = new AdapterDataObservable();
    private final List<T> list_bean = new ArrayList<>();

    public FlowLayoutAdapter(List<T> list_bean) {
        if (list_bean != null) {
            this.list_bean.addAll(list_bean);
        }
    }

    @Override
    public void registerAdapterDataObserver(@NonNull AdapterDataObserver observer) {
        mObservable.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(@NonNull AdapterDataObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        mObservable.notifyChanged();
    }

    @Override
    public int getCount() {
        return list_bean.size();
    }

    @Override
    public View getView(FlowLayout parent, final int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutID(position, list_bean.get(position)), parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position, list_bean.get(position));
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClick(position, list_bean.get(position));
                return true;
            }
        });
        bindDataToView(new ViewHolder(view), position, list_bean.get(position));
        return view;
    }

    public void remove(int position) {
        list_bean.remove(position);
        notifyDataSetChanged();
    }

    public void add(T bean) {
        list_bean.add(bean);
        notifyDataSetChanged();
    }

    public int addAll(List<T> beans) {
        list_bean.addAll(beans);
        notifyDataSetChanged();
        return beans.size();
    }

    public void addAll(Collection<T> c) {
        list_bean.addAll(c);
        notifyDataSetChanged();
    }

    public int clearAddAll(List<T> beans) {
        list_bean.clear();
        list_bean.addAll(beans);
        notifyDataSetChanged();
        return beans.size();
    }

    public void addAllToHead(List<T> beans) {
        list_bean.addAll(0, beans);
        notifyDataSetChanged();
    }

    public void clear() {
        list_bean.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private View itemView;
        private SparseArray<View> array_view;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            array_view = new SparseArray<View>();
        }

        public <T extends View> T getView(int viewId) {
            View view = array_view.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                array_view.put(viewId, view);
            }
            return (T) view;
        }

        public void setText(int tv_id, String text) {
            TextView tv = getView(tv_id);
            tv.setText(nullToString(text));
        }

        public String nullToString(Object object) {
            return object == null ? "" : object.toString();
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {

        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

    public abstract static class AdapterDataObserver {
        public void onChanged() {

        }
    }
}
