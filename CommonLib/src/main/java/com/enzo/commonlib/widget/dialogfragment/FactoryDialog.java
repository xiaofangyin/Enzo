package com.enzo.commonlib.widget.dialogfragment;

import android.app.FragmentManager;

public class FactoryDialog extends BaseDialog {

    public static FactoryDialog create(FragmentManager mFragmentManager) {
        FactoryDialog demoDialog = new FactoryDialog();
        demoDialog.setFragmentManager(mFragmentManager);
        return demoDialog;
    }

}
