package com.robin.mvp.app.first.model;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public interface IFirstModel {
    void loadData(String name, String number);//调用接口(presenter调用，而不是view中调用)
}
