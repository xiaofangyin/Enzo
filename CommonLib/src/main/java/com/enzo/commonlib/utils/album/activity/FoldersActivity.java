package com.enzo.commonlib.utils.album.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.enzo.commonlib.R;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.album.adapter.FoldersAdapter;
import com.enzo.commonlib.utils.album.bean.AlbumFolder;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.utils.SelectImageModel;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;

import java.util.List;

/**
 * 文 件 名: FoldersActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class FoldersActivity extends BaseActivity {

    private boolean isSingle;
    private RecyclerView recyclerView;
    private int mMaxCount;
    private boolean needCrop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_folders;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.folders_header);
        headWidget.setTitle("相册");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.folders_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(FoldersActivity.this));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isSingle = getIntent().getBooleanExtra(SelectImageConstants.IS_SINGLE, false);
        mMaxCount = getIntent().getIntExtra(SelectImageConstants.MAX_SELECT_COUNT, 0);
        needCrop = getIntent().getBooleanExtra(SelectImageConstants.NEED_CROP, false);
        loadImageForSDCard();
    }

    @Override
    public void initListener() {

    }

    private void loadImageForSDCard() {
        SelectImageModel.getFolderList(this, new SelectImageModel.FolderCallback() {
            @Override
            public void onSuccess(List<AlbumFolder> folders) {
                if (folders != null && !folders.isEmpty()) {
                    FoldersAdapter adapter = new FoldersAdapter(FoldersActivity.this, folders);
                    adapter.setOnFolderSelectListener(new FoldersAdapter.OnFolderSelectListener() {
                        @Override
                        public void OnFolderSelect(AlbumFolder folder) {
                            if (isSingle) {
                                Intent intent = new Intent(FoldersActivity.this, SingleSelectorActivity.class);
                                intent.putExtra(SelectImageConstants.BUCKET, folder.getName());
                                intent.putExtra(SelectImageConstants.NEED_CROP, needCrop);
                                startActivityForResult(intent, SelectImageConstants.AVATAR_CROP_REQUEST_CODE);
                            } else {
                                Intent intent = new Intent(FoldersActivity.this, MultipleSelectorActivity.class);
                                intent.putExtra(SelectImageConstants.BUCKET, folder.getName());
                                intent.putExtra(SelectImageConstants.MAX_SELECT_COUNT, mMaxCount);
                                startActivityForResult(intent, SelectImageConstants.IMAGES_SELECT_REQUEST_CODE);
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode: " + requestCode + "...resultCode: " + resultCode);
        if (requestCode == SelectImageConstants.IMAGES_SELECT_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        } else if (requestCode == SelectImageConstants.AVATAR_CROP_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
