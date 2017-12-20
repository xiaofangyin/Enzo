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
import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;

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

    private BluetoothDevice mDevice;
    private BluetoothGatt mBluetoothGatt;
    private TextView deviceName;
    private TextView textView1;
    private String TAG = "AAA";
    private boolean isServiceConnected;

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
        deviceName = (TextView) findViewById(R.id.device_name);
        textView1 = (TextView) findViewById(R.id.recieve_text);

    }

    @Override
    public void initData() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d(TAG, "onLeScan:  " + device.getName() + " === " + "MAC: " + device.getAddress() + "=== rssi ===" + rssi);
            String name = device.getName();
            if (name != null) {
                deviceName.setText(name);
                if (name.contains("xiao")) {
                    mDevice = device;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }
        }
    };

    private void scanLeDevice() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }, 30000);
        mScanning = true;
        // 定义一个回调接口供扫描结束处理
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d(TAG, "onConnectionStateChange: status: " + status + " ... newState: " + newState);

            if (status != BluetoothGatt.GATT_SUCCESS) {
                String err = "Cannot connect device with error status: " + status;
                Log.d(TAG, "err: " + err);
                gatt.close();
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                }
                Log.e(TAG, err);
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
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "onServicesDiscovered: " + "发现服务 : " + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                isServiceConnected = true;

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
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if (UUID_CHARACTERISTIC.equals(characteristic.getUuid().toString())) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d(TAG, "read value: " + characteristic.getValue());
                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d(TAG, "onDescriptorWrite: " + "设置成功");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d(TAG, "onCharacteristicWrite: " + "发送成功");

            boolean b = mBluetoothGatt.setCharacteristicNotification(characteristic, true);
            mBluetoothGatt.readCharacteristic(characteristic);
        }

        @Override
        public final void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            byte[] value = characteristic.getValue();
            Log.d(TAG, "onCharacteristicChanged: " + value);
            final String s0 = Integer.toHexString(value[0] & 0xFF);
            final String s = Integer.toHexString(value[1] & 0xFF);
            Log.d(TAG, "onCharacteristicChanged: " + s0 + "、" + s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView1.setText("收到: " + s0 + "、" + s);
                }
            });
            for (byte b : value) {
                Log.d(TAG, "onCharacteristicChanged: " + b);
            }
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

    public void startConnect(View view) {
        Log.e(TAG, "startConnect...");
        if (mDevice != null) {
            if (mBluetoothGatt != null) {
                mBluetoothGatt.disconnect();
                mBluetoothGatt.close();
                mBluetoothGatt = null;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothGatt = mDevice.connectGatt(MDBleActivity.this, false, mGattCallback);
                }
            }, 2000);
        }
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

    public void startSend(View view) {
        Log.e(TAG, "startSend...");
        if (mBluetoothGatt != null && isServiceConnected) {
            BluetoothGattService gattService = mBluetoothGatt.getService(UUID_SERVICE);
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(UUID_CHARACTERISTIC);
            byte[] bytes = new byte[2];
            bytes[0] = 04;
            bytes[1] = 01;
            characteristic.setValue(bytes);
            characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBluetoothGatt.writeCharacteristic(characteristic);
        }
    }

    public void startRead(View view) {
        Log.e(TAG, "startRead...");
        if (mBluetoothGatt != null && isServiceConnected) {
            BluetoothGattService gattService = mBluetoothGatt.getService(UUID_SERVICE);
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(UUID_DESCRIPTOR);
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

    @Override
    protected void onDestroy() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
        }
        super.onDestroy();
    }

    public void stopConnect(View view) {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
        }
    }
}
