package com.jacky.labeauty.logic.network.transformer;

import com.jacky.labeauty.logic.entity.response.HttpPageResponse;
import com.jacky.labeauty.logic.network.exception.ApiException;
import com.jacky.labeauty.logic.network.exception.CustomException;
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils;

import java.util.List;

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
public class HttpPageResponseTransformer {
    public static <T> ObservableTransformer<HttpPageResponse<T>, HttpPageResponse<T>> handPageResult(boolean loading) {
        return upstream -> upstream.compose(LoadingTransformer.loading(loading))
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponsePageFunction<T>());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends HttpPageResponse<T>>> {

        @Override
        public ObservableSource<? extends HttpPageResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<HttpPageResponse<T>, ObservableSource<List<T>>> {

        @Override
        public ObservableSource<List<T>> apply(HttpPageResponse<T> response) throws Exception {
            int code = response.getCode();
            String message = response.getMessage();
            //Http状态码为200，json响应体的code意义同Restful Http状态码。2XX 正常，4XX 请求有误。5XX 服务器错误
            if (code >= 200 && code < 300) {
                return Observable.just(response.getData());
            } else {
                ApiException exception = new ApiException(code, message);
                CustomException.handleException(exception);
                return Observable.error(exception);
            }
        }
    }

    private static class ResponsePageFunction<T> implements Function<HttpPageResponse<T>, ObservableSource<HttpPageResponse<T>>> {

        @Override
        public ObservableSource<HttpPageResponse<T>> apply(HttpPageResponse<T> response) throws Exception {
            int code = response.getCode();
            String message = response.getMessage();
            //Http状态码为200，json响应体的code意义同Restful Http状态码。2XX 正常，4XX 请求有误。5XX 服务器错误
            if (code >= 200 && code < 300) {
                return Observable.just(response);
            } else {
                ApiException exception = new ApiException(code, message);
                CustomException.handleException(exception);
                return Observable.error(exception);
            }
        }
    }
}
