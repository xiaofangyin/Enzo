package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.shape.OvalShape;
import com.enzo.module_d.R;

public class MDListViewActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_listview;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("引导");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
        initListViews();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ToastUtil.show("Click to show highlight(点击显示高亮)~", Toast.LENGTH_LONG);
    }

    private void initListViews() {
        ListView mListView = findViewById(R.id.listview);
        mListView.setDividerHeight(20);
        mListView.setAdapter(new Adapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showGuide(view);
            }
        });
    }

    private void showGuide(View highlightedView) {
        if (highlightedView == null) {
            return;
        }

        OvalShape ovalShape = new OvalShape();
        ovalShape.setPaint(MDLighterHelper.getDashPaint());

        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedView(highlightedView)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_5)
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

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.md_layout_lighter_item_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.iv_image);
                convertView.setTag(viewHolder);
            }

            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.imageView.setImageResource(MDLighterHelper.sPictureList.get(position));
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
