package com.robin.mvp.app.first.presenter;

import android.util.Log;

import com.robin.mvp.app.first.model.DataModel;
import com.robin.mvp.app.first.model.FirstModelImpl;
import com.robin.mvp.app.first.view.IFirstView;

import static android.content.ContentValues.TAG;

/**
 * Created by Robin on 2017/3/29.
 */

public class FirstPresenterImpl implements IFirstPresenter{
    private FirstModelImpl model;
    private IFirstView view;

    public FirstPresenterImpl(final IFirstView view) {
        this.view = view;
        model = new FirstModelImpl();
        model.setFirstPresenterListener(new FirstModelImpl.FirstPresenterListener() {
            @Override
            public void onSuccess(DataModel value) {
                view.showProgressbar(false);
                view.setData(value);
            }

            @Override
            public void onFailed(String e) {
                view.showProgressbar(false);
                view.showError(e);
            }
        });
    }


    @Override
    public void loadData(String name, String number) {//这个方法就是fragment或者activity中需要调用的。也就是整个流程的开始
        view.showProgressbar(true);
        Log.i(TAG, "loadData: ");
        model.loadData(name,number);
    }
}
