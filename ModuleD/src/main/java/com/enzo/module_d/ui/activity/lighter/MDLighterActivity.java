package com.enzo.module_d.ui.activity.lighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.enzo.module_d.R;

public class MDLighterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighter);
    }

    public void startNormalLayout(View view) {
        startActivity(new Intent(this, MDRelativeLayoutActivity.class));
    }

    public void startRecyclerView(View view) {
        startActivity(new Intent(this, MDRecyclerViewActivity.class));
    }

    public void startListView(View view) {
        startActivity(new Intent(this, MDListViewActivity.class));
    }

    public void startScrollView(View view) {
        startActivity(new Intent(this, MDScrollViewActivity.class));
    }

    public void startGridView(View view) {
        startActivity(new Intent(this, MDGridViewActivity.class));
    }

    public void startViewPager(View view) {
        startActivity(new Intent(this, MDViewPagerActivity.class));
    }

    public void startDialog(View view) {
        startActivity(new Intent(this, MDDialogActivity.class));
    }

    public void startFragment(View view) {
        startActivity(new Intent(this, MDFragmentActivity.class));
    }
}
