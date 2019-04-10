package com.jacky.labeauty.logic.network.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.jacky.labeauty.logic.MiscFacade;
import com.jacky.labeauty.support.log.Logger;
import com.jacky.labeauty.support.util.AndroidUtil;
import com.jacky.labeauty.support.util.Strings;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 2018/10/30.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class CustomException {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;

    //重新登陆
    public static final int ACTION_RELOGIN = 401;


    public static ApiException handleException(Throwable e) {
        Logger.Companion.e(e);

        ApiException ex;
        if (e instanceof ApiException) {
            ex = (ApiException) e;
        } else {
            if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                //解析错误
                ex = new ApiException(PARSE_ERROR, e.getMessage());
            } else if (e instanceof ConnectException) {
                //网络错误
                ex = new ApiException(NETWORK_ERROR, e.getMessage());
            } else if (e instanceof UnknownHostException) {
                //连接错误
                ex = new ApiException(NETWORK_ERROR, "连接异常");
            } else if (e instanceof SocketTimeoutException) {
                ex = new ApiException(NETWORK_ERROR, "连接超时");
            } else {
                //未知错误
                ex = new ApiException(UNKNOWN, e.getMessage());
            }
        }
        AndroidUtil.runUI(() -> handlerExceptionInternal(ex));
        return ex;
    }

    private static void handlerExceptionInternal(ApiException apiException) {
        int code = apiException.getCode();
        if ((code >= 400 && code <= 600) || code >= 1000) {
            if (code == ACTION_RELOGIN) {
                MiscFacade.get().setIsNeedToLogout(true);
            } else {
                if (Strings.isNotBlank(apiException.getDisplayMessage())) {
                    AndroidUtil.toast(apiException.getDisplayMessage());
                }
            }
        }
    }

}
