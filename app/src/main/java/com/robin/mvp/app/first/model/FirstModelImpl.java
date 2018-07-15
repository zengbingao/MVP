package com.robin.mvp.app.first.model;

import com.robin.mvp.common.MyApplication;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Robin on 2017/3/29.
 */

public class FirstModelImpl implements IFirstModel {
    private FirstPresenterListener mFirstPresenterListener;

    public interface FirstPresenterListener {
        void onSuccess(DataModel value);

        void onFailed(String e);
    }
    public void setFirstPresenterListener(FirstPresenterListener mFirstPresenterListener) {
        this.mFirstPresenterListener = mFirstPresenterListener;
    }

    @Override
    public void loadData(String name, String number) {//也可以把FirstPresenterListener作为参数书传进来
        MyApplication.getApi().getRestApi().getData(name, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {//应该是第一个被调用的

                    }

                    @Override
                    public void onNext(DataModel value) {
                        mFirstPresenterListener.onSuccess(value);
//                        loadDataSuccess(value);
//                        mIFirstPresenter.loadDataSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFirstPresenterListener.onFailed(e.toString());
//                        loadDataFailed(e.toString());
//                        mIFirstPresenter.loadDataFailed(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
