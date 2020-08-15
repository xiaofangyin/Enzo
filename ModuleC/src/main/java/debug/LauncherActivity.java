package debug;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.statusbar.bar.StateAppBar;
import com.enzo.module_c.R;
import com.enzo.module_c.ui.fragment.MCFragment;

public class LauncherActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.mc_activity_launcher;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
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
        transaction.replace(R.id.main_content, new MCFragment());
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }
}
