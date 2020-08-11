package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.commonlib.widget.lighter.parameter.MarginOffset;
import com.enzo.commonlib.widget.lighter.shape.RectShape;
import com.enzo.module_d.R;

public class MDGridViewActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_gridview;
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
        initGridView();
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

    private void initGridView() {
        GridView mGridView = findViewById(R.id.gridview);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(new Adapter());
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        RectShape rectShape = new RectShape();
        rectShape.setPaint(MDLighterHelper.getDashPaint());

        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedView(highlightedView)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_6)
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setLighterShape(rectShape)
                        .setShapeXOffset(10)
                        .setShapeYOffset(10)
                        .setTipViewRelativeOffset(new MarginOffset(0, 0, 0, 20))
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
