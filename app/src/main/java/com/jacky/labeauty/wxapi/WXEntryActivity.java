package com.jacky.labeauty.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jacky.labeauty.R;
import com.jacky.labeauty.logic.thridLogin.AuthResult;
import com.jacky.labeauty.logic.thridLogin.AuthThrowable;
import com.jacky.labeauty.logic.thridLogin.OnThirdAuthListener;
import com.jacky.labeauty.logic.thridLogin.Platforms;
import com.jacky.labeauty.logic.thridLogin.ThirdLoginHelper;
import com.jacky.labeauty.support.util.AndroidUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            ThirdLoginHelper.INSTANCE.getWxApi().handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        OnThirdAuthListener listener = ThirdLoginHelper.INSTANCE.getListener();

        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            if (listener != null) {
                if (resp instanceof SendAuth.Resp) {
                    final SendAuth.Resp auth = (SendAuth.Resp) resp;
                    listener.onSuccess(new AuthResult(auth.code, Platforms.WECHAT));
                    finish();
                    return;
                }
            }
        }

        int result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        if (listener != null) {
            listener.onError(new AuthThrowable((String) AndroidUtil.getString(result)));
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThirdLoginHelper.INSTANCE.clear();
    }
}
