package com.kejiang.yuandl.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kejiang.yuandl.R;
import com.kejiang.yuandl.http.HttpResponse;
import com.kejiang.yuandl.http.HttpUtils;
import com.kejiang.yuandl.utils.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * com.bm.falvzixun.activities.BaseActivity;
 *
 * @author yuandl on 2015/12/17.
 *         所有页面的基类
 */
public abstract class BaseActivity extends AutoLayoutActivity implements View.OnClickListener, HttpResponse {
    private TextView mTitleTextView;
    private ImageView mBackwardbButton;
    private TextView mForwardButton;
    private FrameLayout mContentLayout;
    private LinearLayout llRoot;
    private LinearLayout layout_titlebar;
    protected Context context;
    private Callback.Cancelable cancelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setImmersionStatus();
        setupViews();
        context = this;
        initTitleBar();
        initViews();
        initData();
        addListener();
    }

    public TextView getmForwardButton() {
        return mForwardButton;
    }

    public ImageView getmBackwardbButton() {
        return mBackwardbButton;
    }

    /**
     * 初始化设置标题栏
     */
    public abstract void initTitleBar();

    /**
     * 初始化view控件
     */
    public abstract void initViews();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 给view添加事件监听
     */
    public abstract void addListener();

    /**
     * 设置沉浸式状态栏
     */
    private void setImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected FrameLayout getContentLayout() {
        return mContentLayout;
    }

    /**
     * 加载 activity_title 布局 ，并获取标题及两侧按钮
     */
    private void setupViews() {
        super.setContentView(R.layout.ac_title);
        llRoot = (LinearLayout) findViewById(R.id.llRoot);
//        AutoUtils.auto(llRoot);
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        mBackwardbButton = (ImageView) findViewById(R.id.button_backward);
        mForwardButton = (TextView) findViewById(R.id.button_forward);
    }

    /**
     * 设置标题栏是否可见
     *
     * @param visibility
     */
    public void setTitleBarVisible(int visibility) {
        layout_titlebar.setVisibility(visibility);
    }

    /**
     * 设置标题栏的整体背景颜色（含沉浸式状态栏）
     *
     * @param color
     */
    public void setTitleBarBackground(int color) {
        setStatusBarBackground(color);
        layout_titlebar.setBackgroundColor(color);
    }

    /**
     * 设置返回按钮背景图片（含沉浸式状态栏）
     *
     * @param drawable
     */
    public void setBackwardButtonBackgroundDrawable(Drawable drawable, LinearLayout.LayoutParams layoutParams) {
        mBackwardbButton.setImageDrawable(drawable);
        mBackwardbButton.setLayoutParams(layoutParams);
        AutoUtils.auto(mBackwardbButton);
    }

    /**
     * 设置返回按钮背景图片（含沉浸式状态栏）
     *
     * @param drawable
     */
    public void setBackwardButtonBackgroundDrawable(Drawable drawable) {
        mBackwardbButton.setImageDrawable(drawable);
    }

    /**
     * 设置浸式状态栏的整体背景颜色
     *
     * @param color
     */
    public void setStatusBarBackground(int color) {
        llRoot.setBackgroundColor(color);
    }

    /**
     * 设置浸式状态栏的整体背景图片(慎用)
     *
     * @param drawable
     */
    public void setStatusBarBackgroundDrawable(Drawable drawable) {
        llRoot.setBackgroundDrawable(drawable);
    }

    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
//                mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    protected void setBackwardViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (mBackwardbButton != null) {
            mBackwardbButton.setLayoutParams(layoutParams);
        }
    }

    protected void setForwardViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (mForwardButton != null) {
            mForwardButton.setLayoutParams(layoutParams);
        }
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     * @param show         true则显示
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setVisibility(View.VISIBLE);
                mForwardButton.setText(forwardResId);
            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param title 文字
     * @param show  true则显示
     */
    protected void showForwardView(CharSequence title, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setText(title);
                mForwardButton.setVisibility(View.VISIBLE);

            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    public void onBackward(View backwardView) {
        finish();
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    public void onForward(View forwardView) {
        Toast.makeText(this, "点击提交", Toast.LENGTH_LONG).show();
    }

    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }


    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }

    private Toast toast;

    /**
     * 弹出Toast便捷方法
     *
     * @param charSequence
     */
    public void showToast(CharSequence charSequence) {
        if (null == toast) {
            toast = Toast.makeText(x.app(), charSequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(charSequence);
        }
        toast.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != toast) {
            toast.cancel();
        }

    }

    @Override
    public void onClick(View v) {
    }

    /**
     * activity跳转
     *
     * @param activity
     */
    protected void startActivity(Activity activity) {
        startActivity(new Intent(context, activity.getClass()));
    }

    /**
     * 异步网络请求类
     *
     * @param requestParams
     */
    protected void ajax(RequestParams requestParams) {
        ajax(requestParams, -1, true);
    }

    /**
     * 异步网络请求类
     *
     * @param requestParams
     */
    protected void ajax(RequestParams requestParams, boolean isShowLoadingDialog) {
        ajax(requestParams, -1, isShowLoadingDialog);
    }

    /**
     * 异步网络请求类
     *
     * @param requestParams
     * @param requestCode   区分不同的网络请求
     */
    protected void ajax(RequestParams requestParams, int requestCode) {
        ajax(requestParams, requestCode, true);
    }

    /**
     * 异步网络请求类
     *
     * @param requestParams
     * @param requestCode   区分不同的网络请求
     */
    protected void ajax(RequestParams requestParams, int requestCode, boolean isShowLoadingDialog) {
        cancelable = HttpUtils.getInstance().ajax(context, requestParams, requestCode, isShowLoadingDialog, this);
    }

    @Override
    public void onBackPressed() {
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        } else {
            super.onBackPressed();
        }

    }

    protected <T extends View> T setEmptyView(ListView listView) {
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无数据！");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        return (T) emptyView;
    }

    protected <T extends View> T setEmptyView(ListView listView, String text) {
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText(text);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        return (T) emptyView;
    }

    protected <T extends View> T setEmptyView(GridView gridView) {
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无数据！");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) gridView.getParent()).addView(emptyView);
        gridView.setEmptyView(emptyView);
        return (T) emptyView;
    }

    protected <T extends View> T setEmptyView(GridView gridView, String text) {
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText(text);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) gridView.getParent()).addView(emptyView);
        gridView.setEmptyView(emptyView);
        return (T) emptyView;
    }

    @Override
    protected void onDestroy() {
        mContentLayout.removeAllViews();
        mContentLayout = null;
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void netOnStart() {

    }

    @Override
    public void netOnStart(int requestCode) {

    }

    @Override
    public void netOnSuccess(Map<String, Object> data, int requestCode) {

    }

    @Override
    public void netOnSuccess(Map<String, Object> data) {

    }

    @Override
    public void netOnOtherStatus(int status, String msg, int requestCode) {

    }

    @Override
    public void netOnOtherStatus(int status, String msg) {

    }

    @Override
    public void netOnFinish() {

    }

    @Override
    public void netOnFinish(int requestCode) {

    }

    @Override
    public void netOnFailure(Throwable ex) {

    }

    @Override
    public void netOnFailure(int requestCode, Throwable ex) {

    }
}

