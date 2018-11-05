package com.jacky.beedee.logic.network.transformer;

import com.jacky.beedee.logic.entity.response.HttpResponse;
import com.jacky.beedee.logic.entity.response.HttpResponseSource;
import com.jacky.beedee.logic.network.exception.ApiException;
import com.jacky.beedee.logic.network.exception.CustomException;
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class BooleanTransformer {
    public static ObservableTransformer<HttpResponseSource, Boolean> handleResult(boolean loading) {
        return upstream -> upstream
                .compose(LoadingTransformer.loading(loading))
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends HttpResponseSource>> {

        @Override
        public ObservableSource<? extends HttpResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     */
    private static class ResponseFunction implements Function<HttpResponseSource, ObservableSource<Boolean>> {

        @Override
        public ObservableSource<Boolean> apply(HttpResponseSource response) throws Exception {
            int code = response.getCode();
            String message = response.getMessage();
            //Http状态码为200，json响应体的code意义同Restful Http状态码。2XX 正常，4XX 请求有误。5XX 服务器错误
            if (code >= 200 && code < 300) {
                return Observable.just(true);
            } else {
                ApiException exception = new ApiException(code, message);
                CustomException.handleException(exception);
                return Observable.error(exception);
            }
        }
    }
}
