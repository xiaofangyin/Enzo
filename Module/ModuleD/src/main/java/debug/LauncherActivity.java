package debug;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.module_d.R;
import com.enzo.module_d.ui.fragment.MDFragment;

public class LauncherActivity extends BaseActivity {

    @Override
    public int getStatusBarColor() {
        return R.color.color_major_c1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_launcher;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        replaceFragment();
    }

    @Override
    public void initListener() {

    }

    private void replaceFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, new MDFragment());
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }
}
