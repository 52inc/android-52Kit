/*
 * Copyright (c) 2017 52inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ftinc.kit.util;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action0;
import rx.functions.Action1;
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
    public static <T> Observable.Transformer<T, T> applyLogging(final String name, final Logger logger){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .doOnNext(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                logger.log(Log.DEBUG, "[%s] onNext(%s)", name, t);
                            }
                        })
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                logger.log(throwable, Log.DEBUG, "[%s] onError()", name);
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                logger.log(Log.DEBUG, "[%s] onCompleted()", name);
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
    public static <T> Observable.Transformer<T, T> benchmark(final String name, final Logger logger){
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
                                long timeMs = TimeUnit.NANOSECONDS.toMillis(startTime[0]);
                                logger.log(Log.DEBUG, "[%s] Observable Duration: %s", name, TimeUtils.getTimeAgo(timeMs));
                            }
                        });
            }
        };
    }


    public interface Logger {
        void log(int priority, String message, Object... args);
        void log(Throwable e, int priority, String message, Object... args);
    }
}
