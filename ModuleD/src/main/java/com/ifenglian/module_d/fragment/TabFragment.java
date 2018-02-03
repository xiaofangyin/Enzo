package com.ifenglian.module_d.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDStickyNavAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 50; i++) {
            mData.add(" -> " + i);
        }
        MDStickyNavAdapter adapter = new MDStickyNavAdapter();
        adapter.setData(mData);
        mRecyclerView.setAdapter(adapter);
        return view;

    }

    public static TabFragment newInstance(String title) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

}
