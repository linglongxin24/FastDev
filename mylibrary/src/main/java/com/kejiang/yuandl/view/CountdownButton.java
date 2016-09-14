package com.kejiang.yuandl.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义倒计时按钮
 * <p/>
 *
 * @author Dylan
 *         <p/>
 *         [佛祖保佑 永无BUG]
 *         Created by Dylan on 2015/11/5 0005.
 */
public class CountdownButton extends Button implements View.OnClickListener {
    private long lenght = 60 * 1000;//默认倒计时时间；
    private long time;//倒计时时长
    private Timer timer;//开始执行倒计时
    private TimerTask timerTask;//每次倒计时执行的任务
    private String beforeText = "获取验证码";
    private String afterText = "秒后重新获取";
    private OnClickListener onClickListener;//按钮点击事件
    private boolean isStart = false;

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
        start();
    }

    /**
     * 更新显示的文本
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountdownButton.this.setText(time / 1000 + afterText);
            time -= 1000;
            if (time < 0) {
                CountdownButton.this.setEnabled(true);

                CountdownButton.this.setText(beforeText);

                clearTimer();
            }
        }
    };


    public CountdownButton(Context context) {
        super(context);
        this.setText(beforeText);
        setOnClickListener(this);
    }

    public CountdownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public CountdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }


    /**
     * 清除倒计时
     */
    public void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (handler != null) {
            handler.removeCallbacks(timerTask);
        }
        this.setText(beforeText);
        this.setEnabled(true);
    }

    /**
     * 设置倒计时时长
     *
     * @param lenght 默认毫秒
     */
    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    /**
     * 设置未点击时显示的文字
     *
     * @param beforeText
     */
    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    /**
     * 设置未点击后显示的文字
     *
     * @param afterText
     */
    public void setAfterText(String afterText) {
        this.afterText = afterText;
    }

    /**
     * 点击按钮后的操作
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
//        if (isStart) {
//            initTimer();
//            this.setText(time / 1000 + afterText);
//            this.setEnabled(false);
//            timer.schedule(timerTask, 0, 1000);
//        }

    }

    private void start() {
        initTimer();
        this.setText(time / 1000 + afterText);
        this.setEnabled(false);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 初始化时间
     */
    private void initTimer() {
        time = lenght;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
    }

    /**
     * 设置监听按钮点击事件
     *
     * @param onclickListener
     */
    @Override
    public void setOnClickListener(OnClickListener onclickListener) {
        if (onclickListener instanceof CountdownButton) {
            super.setOnClickListener(onclickListener);

        } else {
            this.onClickListener = onclickListener;
        }

    }
}
