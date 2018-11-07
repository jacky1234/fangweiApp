package com.jacky.beedee.logic.image;

import android.annotation.SuppressLint;

import com.bumptech.glide.request.RequestOptions;
import com.jacky.beedee.R;
import com.jacky.beedee.support.util.AndroidUtil;

@SuppressLint("CheckResult")
public class ImageLoader {
    public static final float item_padding = AndroidUtil.dip2px(15);
    public static final RequestOptions defaultRequestOptions = new RequestOptions();
    public static final RequestOptions _16To9RequestOptions = new RequestOptions();
    public static final RequestOptions _1To1RequestOptions = new RequestOptions();

    static {
        defaultRequestOptions.placeholder(R.mipmap.ic_plackholder_header)
                .error(R.mipmap.ic_plackholder_header);

        _16To9RequestOptions.placeholder(R.mipmap.item_empty_16_9)
                .error(R.mipmap.item_empty_16_9);

        _1To1RequestOptions.placeholder(R.mipmap.item_empty_1_1)
                .error(R.mipmap.item_empty_1_1);
    }
}
