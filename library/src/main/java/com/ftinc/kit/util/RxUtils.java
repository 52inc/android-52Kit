package com.ftinc.kit.util;

import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
     * Apply the subscribeOn/observeOn transformation of newThread/mainThread
     * to an observable via compose()
     * </p>
     *
     * <p>
     * Only apply this to observables that are handling work that requires it's own new thread
     * </p>
     *
     * @param <T>       the transformation type
     * @return          the observable post-transform
     */
    public static <T> Observable.Transformer<T, T> applyNewThreadSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.newThread())
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
     * Apply the subscribeOn/observeOn transformation of custom/mainThread
     * to an observable via compose()
     * </p>
     *
     * @param <T>       the transformation type
     * @return          the observable post-transform
     */
    public static <T> Observable.Transformer<T, T> applySchedulers(final Scheduler subscribeScheduler) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(subscribeScheduler)
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
                return observable.subscribeOn(HandlerScheduler.from(subscribeHandler))
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
                return observable.subscribeOn(HandlerScheduler.from(subscribeHandler))
                        .observeOn(HandlerScheduler.from(observeHandler));
            }
        };
    }

    /**
     * <p>
     *     Add a log statement to the onNext, onError, and onCompleted parts of an observable
     * </p>
     *
     * @param name      the log tag name
     * @param <T>       the transformation type
     * @return          the transformer
     */
    public static <T> Observable.Transformer<T, T> applyLogging(final String name){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .doOnNext(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                Timber.d("[%s] onNext(%s)", name, t);
                            }
                        })
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Timber.d(throwable, "[%s] onError()", name);
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                Timber.d("[%s] onCompleted()", name);
                            }
                        });
            }
        };
    }

    /**
     * <p>
     * Benchmark an observable from the point of subscription to the point of completion
     * </p>
     *
     * @param name      the name of the observable to print in log statement
     * @param <T>       The transformation type
     * @return          The transformer
     */
    public static <T> Observable.Transformer<T, T> benchmark(final String name){
        final long[] startTime = new long[1];
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                startTime[0] = System.nanoTime();
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                long diff = System.nanoTime() - startTime[0];
                                long timeMs = TimeUnit.NANOSECONDS.toMillis(diff);
                                Timber.d("[%s] Observable Duration: %s", name, TimeUtils.getTimeAgo(timeMs));
                            }
                        });
            }
        };
    }

}
