package com.jacky.beedee.logic.image;

import android.annotation.SuppressLint;

import com.bumptech.glide.request.RequestOptions;
import com.jacky.beedee.R;

@SuppressLint("CheckResult")
public class ImageLoader {
    public static final RequestOptions defaultRequestOptions = new RequestOptions();
    public static final RequestOptions _16To9RequestOptions = new RequestOptions();

    static {
        defaultRequestOptions.placeholder(R.mipmap.ic_plackholder_header)
                .error(R.mipmap.ic_plackholder_header);

        _16To9RequestOptions.placeholder(R.mipmap.item_empty_19_6)
                .error(R.mipmap.item_empty_19_6);
    }
}
