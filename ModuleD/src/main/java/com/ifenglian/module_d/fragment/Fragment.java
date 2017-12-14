package com.ifenglian.module_d.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifenglian.module_d.R;

/**
 * Created by MJJ on 2015/7/29.
 */
public class Fragment extends android.support.v4.app.Fragment {

    private int number;

    public Fragment(int number) {
        this.number = number;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        ((TextView)view.findViewById(R.id.text)).setText(""+number);
        ((TextView)view.findViewById(R.id.text)).setTextSize(TypedValue.COMPLEX_UNIT_DIP,100);
        return view;
    }
}
