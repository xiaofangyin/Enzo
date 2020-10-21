package com.enzo.skin.manager.entity;

import com.enzo.skin.manager.loader.SkinManager;
import com.enzo.skin.manager.util.L;

import android.view.View;
import android.widget.TextView;

public class TextColorAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof TextView){
			TextView tv = (TextView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				L.e("attr1", "TextColorAttr");
				tv.setTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
			}
		}
	}
}
