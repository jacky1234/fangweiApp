package com.jacky.lebeauty.ui.widget.bottombar;

import android.view.MotionEvent;
import android.view.View;

/**
 * 双击事件
 *
 * @author: jiangzhiguo
 * @email: jiangzhiguo@xiaoheiban.cn
 * @date: 2018/4/26
 */
public class OnDoubleClickListener implements View.OnTouchListener {
    private int count = 0;
    private long firClick = 0;
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int interval = 300;
    private DoubleClickCallback mCallback;

    public interface DoubleClickCallback {
        void onDoubleClick();
    }

    public OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            count++;
            if (1 == count) {
                firClick = System.currentTimeMillis();
            } else if (2 == count) {
                long secClick = System.currentTimeMillis();
                if (secClick - firClick < interval) {
                    if (mCallback != null) {
                        mCallback.onDoubleClick();
                    }
                    count = 0;
                    firClick = 0;
                } else {
                    firClick = secClick;
                    count = 1;
                }
            }
        }
        return false;
    }
}
