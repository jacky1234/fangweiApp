package com.jacky.beedee.logic.network;

import com.jacky.beedee.logic.entity.response.HttpResponse;
import com.jacky.beedee.logic.network.exception.ApiException;
import com.jacky.beedee.logic.network.exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 2018/10/30.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class ResponseTransformer {
    public static <T> ObservableTransformer<HttpResponse<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends HttpResponse<T>>> {

        @Override
        public ObservableSource<? extends HttpResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<HttpResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(HttpResponse<T> response) throws Exception {
            int code = response.getCode();
            String message = response.getMessage();
            if (code == 200) {
                return Observable.just(response.getData());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }
}
