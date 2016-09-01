/**
 *
 */
package com.kejiang.yuandl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * File_name com.dy.logs.mp.ui.MyListView
 *
 * @author linglongxin24 <br/>
 * @date create at 2014-6-6����2:09:42 <br/>
 * @time update at 2014-6-6����2:09:42
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		// TODO Auto-generated method stub
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated method stub
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
