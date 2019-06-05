package com.example.qrscanlibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView zxingView;
    private TextView lightSetView;
    private ViewGroup parent_back;
    private LinearLayout parentResultContainer;
    private boolean isFlashLightOpen = false;
    private boolean isStopCamera = false;
    private ZxingImageHelper zxingImageHelper;
    private int actionKey;
    private final static String KEY_ACTION_KEY = "KEY_ACTION_KEY";

    public static void launch(Activity activity, int actionKey) {
        final Intent intent = new Intent(activity, ScanCodeActivity.class);
        intent.putExtra(KEY_ACTION_KEY, actionKey);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        actionKey = getIntent().getIntExtra(KEY_ACTION_KEY, -1);
        zxingImageHelper = new ZxingImageHelper(actionKey);

        zxingView = findViewById(R.id.zxing_view);
        lightSetView = findViewById(R.id.tv_light_set);
        parent_back = findViewById(R.id.parent_back);
        parentResultContainer = findViewById(R.id.parent_result);
        zxingView.setDelegate(this);
        initView();
        initListener();
    }

    private void initView() {
        Drawable lightClose = getResources().getDrawable(R.drawable.light_close);
        lightClose.setBounds(0, 0, 50, 50);
        lightSetView.setCompoundDrawables(null, lightClose, null, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isStopCamera) {
            startCameraAndSpot();
        }
    }

    @Override
    protected void onStop() {
        zxingView.stopCamera();
        closeLightAndHidImg();
        //isStopCamera状态重置
        isStopCamera = false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingView.onDestroy();
        super.onDestroy();
        QrScanFacade.releaseAction(actionKey);
    }

    private void initListener() {
        lightSetView.setOnClickListener(v -> {
            if (!isFlashLightOpen) {
                isFlashLightOpen = true;
                lightSet(true);
            } else {
                isFlashLightOpen = false;
                lightSet(false);
            }
        });
        parent_back.setOnClickListener(v -> finish());
        parentResultContainer.setOnClickListener(v -> {
            //点击继续扫码
            parentResultContainer.setVisibility(View.GONE);
            startCameraAndSpot();
        });
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        if (!TextUtils.isEmpty(result)) {
            QrScanFacade.onResult(actionKey, result, null);
            finish();
        } else {
            zxingView.startSpotAndShowRect();
        }
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        if (isDark) {
            if (lightSetView.getVisibility() == View.GONE) {
                lightSetView.setVisibility(View.VISIBLE);
            }
        } else {
            if (!isFlashLightOpen) {
                lightSetView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
//        AppToast.showToast(R.string.apply_camera_permission);
    }

    private void startCameraAndSpot() {
        if (parentResultContainer.getVisibility() == View.VISIBLE) {
            parentResultContainer.setVisibility(View.GONE);
        }
        zxingView.startCamera();
        zxingView.startSpotAndShowRect();
        isStopCamera = false;
    }

    /**
     * 闪光灯设置
     *
     * @param isStateOpen
     */
    private void lightSet(boolean isStateOpen) {
        if (isStateOpen) {
            zxingView.openFlashlight();
            Drawable lightOpen = getResources().getDrawable(R.drawable.light_open);
            lightOpen.setBounds(0, 0, 50, 50);
            lightSetView.setCompoundDrawables(null, lightOpen, null, null);
            lightSetView.setText(R.string.flash_light_close);
        } else {
            zxingView.closeFlashlight();
            Drawable lightClose = getResources().getDrawable(R.drawable.light_close);
            lightClose.setBounds(0, 0, 50, 50);
            lightSetView.setCompoundDrawables(null, lightClose, null, null);
            lightSetView.setText(R.string.flash_light_open);
        }

    }

//    /**
//     * 识别图片二维码
//     */
//    private void handlerQRCode(File imageFile) {
//        if (imageFile.exists()) {
//            if (zxingImageHelper == null) {
//                zxingImageHelper = new ZxingImageHelper();
//            }
//            zxingImageHelper.decode(imageFile.getPath(),
//                    new ZxingImageHelper.OnZxingDecodeListener() {
//                        @Override
//                        public void onDecodeSuccess(String result) {
//                            onScanSuccess(result);
//                        }
//
//                        @Override
//                        public void onDecodeFail() {
//                            onRecognizeFailed();
//                        }
//                    });
//        } else {
//            AppToast.showToast(R.string.image_not_exist);
//        }
//    }

    /**
     * 扫码成功后，震动提示
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
    }

    /**
     * 图片二维码识别失败
     */
    public void onRecognizeFailed() {
        parentResultContainer.setVisibility(View.VISIBLE);
        zxingView.stopCamera();
        zxingView.showScanRect();
        zxingView.stopSpot();
        isStopCamera = true;
        closeLightAndHidImg();
    }

    /**
     * 关闭闪光灯，隐藏灯图标
     */
    private void closeLightAndHidImg() {
        if (isFlashLightOpen) {
            isFlashLightOpen = false;
            lightSet(false);
        }
        lightSetView.setVisibility(View.GONE);
    }
}
