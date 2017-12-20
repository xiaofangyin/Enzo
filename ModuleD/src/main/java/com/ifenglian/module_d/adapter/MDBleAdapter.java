package com.ifenglian.module_d.adapter;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifenglian.module_d.R;

import java.util.List;

/**
 * 文 件 名: MDBleAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/20
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDBleAdapter extends BaseAdapter {

    private List<BluetoothDevice> deviceList;

    public void setData(List<BluetoothDevice> list) {
        deviceList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.md_item_ble_list, null);
        }
        TextView tvName = convertView.findViewById(R.id.tv_ble_name);
        TextView tvMac = convertView.findViewById(R.id.tv_ble_mac);
        tvName.setText(deviceList.get(position).getName());
        tvMac.setText(deviceList.get(position).getAddress());
        return convertView;
    }
}
