package com.ftinc.kit.util;

import android.os.Handler;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Utility Helper class for RxJava additions
 *
 * Project: android-52Kit
 * Package: com.ftinc.kit.util
 * Created by drew.heavner on 5/7/15.
 */
public class RxUtils {

    /**
     * <p>
     * Apply the subscribeOn/observeOn transformation of io/mainThread
     * to an observable via compose()
     * </p>
     *
     * <p>
     * Only apply this to observables that are handling I/O work, e.g.; Networking, Database, etc...
     * </p>
     *
     * @param <T>       the transformation type
     * @return          the observable post-transform
     */
    public static <T> Observable.Transformer<T, T> applyIOSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * <p>
     * Apply the subscribeOn/observeOn transformation of computation/mainThread
     * to an observable via compose()
     * </p>
     *
     * <p>
     * Only apply this to observables that are handling computation tasks in the background. Basically
     * all non-IO work
     * </p>
     *
     * @param <T>       the transformation type
     * @return          the observable post-transform
     */
    public static <T> Observable.Transformer<T, T> applyWorkSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * <p>
     * Apply the subscribeOn/observeOn transformation of {custom_handler}/mainThread
     * to an observable via compose()
     * </p>
     *
     * <p>
     * Only apply this to observables that are handling computation tasks in the background. Basically
     * all non-IO work
     * </p>
     *
     * @param <T>       the transformation type
     * @return          the observable post-transform
     */
    public static <T> Observable.Transformer<T, T> applyCustomSchedulers(final Handler subscribeHandler) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(AndroidSchedulers.handlerThread(subscribeHandler))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * <p>
     * Apply the subscribeOn/observeOn transformation of computation/mainThread
     * to an observable via compose()
     * </p>
     *
     * <p>
     * Only apply this to observables that are handling computation tasks in the background. Basically
     * all non-IO work
     * </p>
     *
     * @param <T>       the transformation type
     * @return          the observable post-transform
     */
    public static <T> Observable.Transformer<T, T> applyCustomSchedulers(final Handler subscribeHandler, final Handler observeHandler) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(AndroidSchedulers.handlerThread(subscribeHandler))
                        .observeOn(AndroidSchedulers.handlerThread(observeHandler));
            }
        };
    }

}
