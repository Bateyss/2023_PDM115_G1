package com.ues.sv.proyecto.controladministrativoapp.utils;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DisposableUtils {

    private static final CompositeDisposable mDisposable = new CompositeDisposable();

    public static void addComposite(CompositeCompletableCallback compositeInterface) {
        mDisposable.add(
                compositeInterface.completableAction()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(compositeInterface::onCallback, throwable -> {
                            Log.e("ADD_COMPLETABLE_COMPOSITE", "Error ejecutar completable", throwable);
                            compositeInterface.onThrow(throwable);
                        }));
    }

    public static void addComposite(CompositeSingleCallbac compositeInterface) {
        mDisposable.add(compositeInterface.completableCallBack(
                compositeInterface.singleAction()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable ->
                                Log.e("ADD_FLOWABLEs_COMPOSITE", "Error ejecutar flowable"
                                        , throwable))));
    }

    public static void addComposite(CompositeFlowableCallback compositeInterface) {
        mDisposable.add(compositeInterface.completableCallBack(
                compositeInterface.flowableAction()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable ->
                                Log.e("ADD_FLOWABLEs_COMPOSITE", "Error ejecutar flowable"
                                        , throwable))));
    }


    public interface CompositeCompletableCallback {

        Completable completableAction();

        void onCallback();

        void onThrow(Throwable throwable);

    }

    public interface CompositeSingleCallbac {

        Single<?> singleAction();

        Disposable completableCallBack(Single<?> applySubscribe);

    }

    public interface CompositeFlowableCallback {

        Flowable<?> flowableAction();

        Disposable completableCallBack(Flowable<?> applySubscribe);

    }

}
