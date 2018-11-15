package com.jacky.beedee.support.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jacky.beedee.support.Starter;

import org.jetbrains.annotations.Nullable;


/**
 * 2018/10/29.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class AndroidUtil {
    private static Handler uiHandle = new Handler(Looper.getMainLooper());

    public static int getScreenWidth() {
        DisplayMetrics dm = Starter.getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = Starter.getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float dip2px(float dipValue) {
        final float scale = Starter.getContext().getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    /**
     * 获取应用版本信息
     */
    public static String getVersionName() {
        String versionName;
        try {
            PackageManager packageManager = Starter.getContext().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(Starter.getContext().getPackageName(), 0);
            versionName = packInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

    public static void toast(int id) {
        toast(getString(id));
    }

    public static void toast(CharSequence msg) {
        Toast.makeText(Starter.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static CharSequence getString(int id) {
        return Starter.getContext().getString(id);
    }

    public static void runUI(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            uiHandle.post(runnable);
        }
    }

    public static void runUI(Runnable runnable, long delay) {
        uiHandle.postDelayed(runnable, delay);
    }

    @Nullable
    public static String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = Starter.getContext().getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }

    public static void showKeyBoard(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) Starter.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }

    public static void setMaxLengthForEditText(EditText mEditText, int maxLength) {
        InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
        mEditText.setFilters(filters);
    }
}
