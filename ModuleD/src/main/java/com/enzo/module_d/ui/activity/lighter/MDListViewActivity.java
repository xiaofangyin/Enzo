package com.enzo.module_d.ui.activity.lighter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.module_d.R;

public class MDListViewActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        setContentView(R.layout.activity_lighter_listview);

        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
        initListViews();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Toast.makeText(this, "Click to show highlight(点击显示高亮)~", Toast.LENGTH_LONG).show();
    }

    private void initListViews(){
        mListView = findViewById(R.id.listview);
        mListView.setDividerHeight(20);
        mListView.setAdapter(new Adapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showGuide(view);
            }
        });
    }

    private void showGuide(View highlightedView){
        if (highlightedView == null){
            return;
        }

        OvalShape ovalShape = new OvalShape();
        ovalShape.setPaint(MDLighterHelper.getDashPaint());

        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedView(highlightedView)
                        .setTipLayoutId(R.layout.layout_lighter_tip_5)
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setLighterShape(ovalShape)
                        .build())
                .show();
    }

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return MDLighterHelper.sPictureList.size();
        }

        @Override
        public Object getItem(int position) {
            return MDLighterHelper.sPictureList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.layout_lighter_item_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.iv_image);
                convertView.setTag(viewHolder);
            }

            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.imageView.setImageResource(MDLighterHelper.sPictureList.get(position));
            return convertView;
        }
    }

    private class ViewHolder{
        ImageView imageView;
    }
}
