package com.enzo.commonlib.widget.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.enzo.commonlib.R;

public abstract class BaseDialog extends DialogFragment {

    /**
     * tag
     */
    private static final String TAG = "BaseDialog";

    /**
     * FragmentManager
     */
    public FragmentManager mFragmentManager;

    /**
     * dialog操作监听回调
     */
    private DialogViewListener mDialogViewListener;

    /**
     * 是否点击其他区域关闭 默认关闭
     */
    private boolean mOutsideCancel = true;

    /**
     * 返回键是否能撤销dialog
     */
    private boolean mBackCancel = true;

    /**
     * tag标记
     */
    private String mTag = "BaseDialog";

    /**
     * 布局文件
     */
    public int mLayoutRes;


    public interface DialogViewListener {

        void bindView(View v);

        void dismiss();
    }

    public BaseDialog() {

    }

    public BaseDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View body = inflater.inflate(getLayoutRes(), null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(body);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(mOutsideCancel);
        setCancelable(mBackCancel);
        bindView(body);
        return dialog;
    }

    public void bindView(View v) {
        if (mDialogViewListener != null) {
            mDialogViewListener.bindView(v);
        }
    }


    /**
     * show显示dialog
     *
     * @param fragmentManager
     */
    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    public BaseDialog show() {
        show(mFragmentManager);
        return this;
    }

    @LayoutRes
    public int getLayoutRes() {
        return mLayoutRes;
    }

    public BaseDialog setDialogViewListener(DialogViewListener listener) {
        mDialogViewListener = listener;
        return this;
    }

    public BaseDialog setmOutsideCancel(boolean mOutsideCancel) {
        this.mOutsideCancel = mOutsideCancel;
        return this;
    }

    public BaseDialog setmBackCancel(boolean mBackCancel) {
        this.mBackCancel = mBackCancel;
        return this;
    }

    public BaseDialog setmTag(String mTag) {
        this.mTag = mTag;
        return this;
    }

    public BaseDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogViewListener != null) {
            mDialogViewListener.dismiss();
        }
    }
}
