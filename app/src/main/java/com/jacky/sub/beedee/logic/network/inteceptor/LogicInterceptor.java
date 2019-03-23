package com.jacky.sub.beedee.logic.network.inteceptor;

import com.jacky.sub.beedee.logic.entity.module.MySelf;
import com.jacky.sub.beedee.support.util.Strings;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogicInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        Headers headers = response.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            if (Strings.equalNames(headers.name(i), "Authorization")) {
                MySelf.get().setAuthorization(headers.value(i));
                MySelf.get().save();
                break;
            }
        }

        return response;
    }
}
