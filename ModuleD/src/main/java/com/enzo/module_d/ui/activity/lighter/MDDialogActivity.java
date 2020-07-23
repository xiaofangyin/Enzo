package com.enzo.module_d.ui.activity.lighter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.enzo.module_d.R;

public class MDDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighter_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
    }

    private void showDialog() {
        new MDCustomDialog(this).show();
    }

    public void showDialog(View view) {
        showDialog();
    }
}
