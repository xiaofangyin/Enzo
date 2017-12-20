package com.ifenglian.module_d.activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDBleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文 件 名: MDBleActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/19
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDBleActivity extends BaseActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler = new Handler();
    private boolean mScanning;
    final UUID UUID_SERVICE = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    final UUID UUID_CHARACTERISTIC = UUID.fromString("00002902-0000-1000-8000-00805f9b34fc");  // 用于发送数据到设备
    final UUID UUID_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fd"); // 用于接收设备推送的数据

    private BluetoothGatt mBluetoothGatt;
    private String TAG = "AAA";
    private boolean isServiceConnected;
    private ListView listView;
    private MDBleAdapter adapter;
    private List<BluetoothDevice> deviceList;
    private EditText editText;
    private TextView tvConnectStatus;
    private TextView tvContent;

    @Override
    public int getLayoutId() {
        return R.layout.md_avtivity_ble;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            if (this.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
        tvConnectStatus = findViewById(R.id.tv_connect_name);
        tvContent = findViewById(R.id.tv_content);
        listView = findViewById(R.id.lv_scan_list);
        editText = findViewById(R.id.et_data);
    }

    @Override
    public void initData() {
        adapter = new MDBleAdapter();
        deviceList = new ArrayList<>();
        listView.setAdapter(adapter);

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "startConnect...");
                tvConnectStatus.setText("正在连接...");
                final BluetoothDevice device = deviceList.get(position);
                if (device != null) {
                    if (mBluetoothGatt != null) {
                        mBluetoothGatt.disconnect();
                        mBluetoothGatt.close();
                        mBluetoothGatt = null;
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBluetoothGatt = device.connectGatt(MDBleActivity.this, false, mGattCallback);
                        }
                    }, 2000);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d(TAG, "onLeScan:  " + device.getName() + " === " + "MAC: " + device.getAddress() + "=== rssi ===" + rssi);
            if (!deviceList.contains(device)) {
                deviceList.add(device);
                adapter.setData(deviceList);
            }
        }
    };

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.e(TAG, "onConnectionStateChange: status: " + status + " ... newState: " + newState);

            if (status != BluetoothGatt.GATT_SUCCESS) {
                String err = "Cannot connect device with error status: " + status;
                Log.d(TAG, "err: " + err);
                gatt.close();
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvConnectStatus.setText("未连接");
                    }
                });
                return;
            }

            if (newState == BluetoothProfile.STATE_CONNECTED) {//当蓝牙设备已经连接
                Log.d(TAG, "onConnectionStateChange: " + "连接成功");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "discover value: " + mBluetoothGatt.discoverServices());

                    }
                }, 2000);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//当设备无法连接
                Log.d(TAG, "onConnectionStateChange: " + "当设备无法连接");
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                }
                gatt.close();
            }
        }

        //发现服务回调。
        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            Log.e(TAG, "onServicesDiscovered: " + "发现服务 : " + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                isServiceConnected = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvConnectStatus.setText(gatt.getDevice().getName());
                    }
                });
                Log.d(TAG, "onServicesDiscovered: " + "发现服务 : " + status);
                Log.d(TAG, "onServicesDiscovered: " + "读取数据0");
                Log.d(TAG, "mBluetoothGatt: " + (mBluetoothGatt == null) + "=== isServiceConnected: " + isServiceConnected);

                if (mBluetoothGatt != null && isServiceConnected) {
                    BluetoothGattService gattService = mBluetoothGatt.getService(UUID_SERVICE);
                    BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(UUID_CHARACTERISTIC);
                    boolean b = mBluetoothGatt.setCharacteristicNotification(characteristic, true);
                    if (b) {
                        List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors();
                        for (BluetoothGattDescriptor descriptor : descriptors) {

                            boolean b1 = descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                            if (b1) {
                                mBluetoothGatt.writeDescriptor(descriptor);
                                Log.d(TAG, "onServicesDiscovered: " + "监听收数据");
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.e(TAG, "onDescriptorWrite...");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.e(TAG, "onDescriptorRead...");
        }

        @Override
        public void onCharacteristicWrite(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.e(TAG, "onCharacteristicWrite... " + "发送成功");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    byte[] text = characteristic.getValue();
                    StringBuilder builder = new StringBuilder();
                    builder.append(tvContent.getText().toString()).append("发送: ").append(Arrays.toString(text)).append("\n");
                    tvContent.setText(builder.toString());
                }
            });
            boolean b = mBluetoothGatt.setCharacteristicNotification(characteristic, true);
            mBluetoothGatt.readCharacteristic(characteristic);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.e(TAG, "onCharacteristicRead...read value: " + Arrays.toString(characteristic.getValue()) + "...status: " + status);
            if (UUID_CHARACTERISTIC.equals(characteristic.getUuid().toString())) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d(TAG, "read value: " + characteristic.getValue());
                }
            }
        }

        @Override
        public final void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            final byte[] value = characteristic.getValue();
            Log.e(TAG, "onCharacteristicChanged: " + Arrays.toString(value));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String text = "收到: " + Arrays.toString(value);
                    StringBuilder builder = new StringBuilder();
                    builder.append(tvContent.getText().toString()).append(text).append("\n");
                    tvContent.setText(builder.toString());
                }
            });
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startScan(View view) {
        Log.e(TAG, "startScan...");
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        if (mScanning) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        scanLeDevice();
    }

    private void scanLeDevice() {
        deviceList.clear();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }, 10000);
        mScanning = true;
        // 定义一个回调接口供扫描结束处理
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    public void startSend(View view) {
        Log.e(TAG, "startSend...");
        if (mBluetoothGatt != null && isServiceConnected) {
            BluetoothGattService gattService = mBluetoothGatt.getService(UUID_SERVICE);
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(UUID_CHARACTERISTIC);

            String text = editText.getText().toString();
            characteristic.setValue(text.getBytes());
            characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBluetoothGatt.writeCharacteristic(characteristic);
        }
    }

    public void startRead(View view) {
        Log.e(TAG, "startRead...");
        if (mBluetoothGatt != null && isServiceConnected) {
            BluetoothGattService gattService = mBluetoothGatt.getService(UUID_SERVICE);
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(UUID_CHARACTERISTIC);
            boolean b = mBluetoothGatt.setCharacteristicNotification(characteristic, true);
            if (b) {
                List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors();
                for (BluetoothGattDescriptor descriptor : descriptors) {

                    boolean b1 = descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                    if (b1) {
                        mBluetoothGatt.writeDescriptor(descriptor);
                        Log.d(TAG, "startRead: " + "监听收数据");
                    }
                }
            }
        }
    }

    public void stopConnect(View view) {
        if (mBluetoothGatt != null) {
            tvConnectStatus.setText("未连接");
            mBluetoothGatt.close();
        }
    }

    @Override
    protected void onDestroy() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
        }
        super.onDestroy();
    }
}
