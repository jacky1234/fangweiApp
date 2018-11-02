package com.jacky.beedee.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jacky.beedee.R;

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class RowItemView extends RelativeLayout {
    public final static int FLAG_NONE = 0;
    public final static int FLAG_RIGHT_SHOW_IMAGE = 1;
    public final static int FLAG_RIGHT_SHOW_TEXT = 1 << 1;
    private int flag = FLAG_RIGHT_SHOW_TEXT;

    private TextView tv_title;
    private View iv_arrow;
    private ImageView iv_image;
    private TextView tv_right_desc;

    public RowItemView(Context context) {
        this(context, null);
    }

    public RowItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RowItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.row_list_item, this, true);

        tv_title = findViewById(R.id.tv_title);
        iv_arrow = findViewById(R.id.iv_arrow);
        iv_image = findViewById(R.id.tv_image);
        tv_right_desc = findViewById(R.id.tv_right_desc);
    }

    public void setType(int type) {
        this.flag = type;
        iv_image.setVisibility((flag & FLAG_RIGHT_SHOW_IMAGE) != 0 ? VISIBLE : GONE);
        tv_right_desc.setVisibility((flag & FLAG_RIGHT_SHOW_TEXT) != 0 ? VISIBLE : GONE);
    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setRightDesc(String text) {
        tv_right_desc.setText(text);
    }

    public ImageView getImageView() {
        return iv_image;
    }
}
