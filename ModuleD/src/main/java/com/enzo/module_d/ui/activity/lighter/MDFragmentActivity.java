package com.enzo.module_d.ui.activity.lighter;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.enzo.module_d.R;

public class MDFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        setContentView(R.layout.activity_lighter_fragment);
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_content, new MDLighterFragment())
                .commit();
    }
}
