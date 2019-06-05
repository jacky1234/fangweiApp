package com.jacky.labeauty.logic.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jacky.labeauty.R;
import com.jacky.labeauty.support.image.MyGlideEngine;
import com.jacky.labeauty.support.util.AndroidUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.SelectionCreator;

import org.jetbrains.annotations.NotNull;

@SuppressLint("CheckResult")
public class ImageLoader {
    public static final float item_padding = AndroidUtil.dip2px(15);
    public static final int angle = (int) AndroidUtil.dip2px(2);
    public static final RequestOptions defaultRequestOptions = new RequestOptions();
    public static final RequestOptions _16To9RequestOptions = new RequestOptions();
    public static final RequestOptions _16To9RequestRoundCornerOptions = RequestOptions.bitmapTransform(new RoundedCorners(angle));
    public static final RequestOptions _1To1RequestOptions = new RequestOptions();

    static {
        defaultRequestOptions.placeholder(R.mipmap.ic_plackholder_header)
                .error(R.mipmap.ic_plackholder_header);

        _16To9RequestOptions.placeholder(R.drawable.item_empty_16_9)
                .error(R.drawable.item_empty_16_9);

        _16To9RequestRoundCornerOptions.apply(_16To9RequestOptions);

        _1To1RequestOptions.placeholder(R.drawable.item_empty_1_1)
                .error(R.drawable.item_empty_1_1);
    }

    /**
     * open gallery to choose
     *
     * @param activity
     * @return
     */
    public static SelectionCreator chooseImageFromGallery(@NotNull Activity activity) {
        return Matisse.from(activity)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .gridExpectedSize(AndroidUtil.getScreenWidth() / 3 - 10)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(MyGlideEngine.DEFAULT);
    }
}
