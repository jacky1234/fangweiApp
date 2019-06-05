package com.example.qrscanlibrary;

import android.graphics.Bitmap;
import android.text.TextUtils;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;


public class ZxingImageHelper implements Closeable {

    static {
        System.loadLibrary("iconv");
    }

    private ImageScanner imageScanner;
    private int actionKey;

    public ZxingImageHelper(int actionKey) {
        this.actionKey = actionKey;
        imageScanner = new ImageScanner();
    }

    private String getQRCodeContent(Bitmap bitmap) {
        String result = QRCodeDecoder.syncDecodeQRCode(bitmap);
        if (TextUtils.isEmpty(result)) {
            result = decodeToZBar(bitmap);
        }
        return result;
    }

    private String getQRCodeContent(String imagePath) {
        String result = QRCodeDecoder.syncDecodeQRCode(imagePath);
        if (TextUtils.isEmpty(result)) {
            result = decodeToZBar(BGAQRCodeUtil.getDecodeAbleBitmap(imagePath));
        }
        return result;
    }

    public void decode(Bitmap bitmap, OnDecodeListener listener) {
        String result = getQRCodeContent(bitmap);
        handlerDecodeSuccess(result);
    }

    public void decode(String imagePath, OnDecodeListener listener) {
        String result = getQRCodeContent(imagePath);
        handlerDecodeSuccess(result);
    }

    private void handlerDecodeSuccess(String result) {
        QrScanFacade.onResult(actionKey, result, null);
    }

    private String decodeToZBar(Bitmap bitmap) {
        try {
            int picWidth = bitmap.getWidth();
            int picHeight = bitmap.getHeight();
            Image barcode = new Image(picWidth, picHeight, "RGB4");
            int[] pix = new int[picWidth * picHeight];
            bitmap.getPixels(pix, 0, picWidth, 0, 0, picWidth, picHeight);
            barcode.setData(pix);
            return processData(barcode.convert("Y800"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String processData(Image barcode) {
        String result = null;
        if (imageScanner.scanImage(barcode) != 0) {
            SymbolSet symbolSet = imageScanner.getResults();
            for (Symbol symbol : symbolSet) {
                String symData;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    symData = new String(symbol.getDataBytes(), StandardCharsets.UTF_8);
                } else {
                    symData = symbol.getData();
                }
                if (!TextUtils.isEmpty(symData)) {
                    result = symData;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public interface OnDecodeListener {

        void onDecodeSuccess(String result);

        void onDecodeFail(Throwable e);
    }
}
