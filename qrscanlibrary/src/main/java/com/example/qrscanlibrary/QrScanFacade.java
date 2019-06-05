package com.example.qrscanlibrary;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;

public class QrScanFacade {
    private final static SparseArray<ZxingImageHelper.OnDecodeListener> LISTENER_SPARSE_ARRAY = new SparseArray<>(2);
    private static int key = 0;

    public static void start(@NonNull Activity activity, @NonNull ZxingImageHelper.OnDecodeListener onDecodeListener) {
        final int key = getRandomActionKey();
        ScanCodeActivity.launch(activity, key);
        LISTENER_SPARSE_ARRAY.put(key, onDecodeListener);
    }

    static void onResult(int actionKey, String result, Throwable throwable) {
        final ZxingImageHelper.OnDecodeListener onDecodeListener = LISTENER_SPARSE_ARRAY.get(actionKey);
        releaseAction(actionKey);
        if (onDecodeListener != null) {
            if (!TextUtils.isEmpty(result)) {
                onDecodeListener.onDecodeSuccess(result);
            } else {
                onDecodeListener.onDecodeFail(throwable);
            }
        }
    }

    private static int getRandomActionKey() {
        return ++key;
    }

    static void releaseAction(int actionKey) {
        LISTENER_SPARSE_ARRAY.remove(actionKey);
    }
}
