package com.robin.mvp.app.first.view;

import com.robin.mvp.app.first.model.DataModel;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public interface IFirstView {
    void showProgressbar(boolean isShow);
    void setData(DataModel model);//if success
    void showError(String Msg);//if failed
}
