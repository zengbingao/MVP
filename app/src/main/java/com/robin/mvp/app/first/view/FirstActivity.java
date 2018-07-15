package com.robin.mvp.app.first.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.robin.mvp.app.first.model.DataModel;
import com.robin.mvp.app.first.presenter.FirstPresenterImpl;
import com.robin.mvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends AppCompatActivity implements IFirstView {
    private static final String TAG = "FirstActivity";
    @BindView(R.id.progress)
    ProgressBar progress;
    private FirstPresenterImpl firstPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        firstPresenter = new FirstPresenterImpl(this);
        firstPresenter.loadData("Android", "10");
    }

    @Override
    public void showProgressbar(boolean isShow) {
        if (isShow) {
            progress.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(DataModel model) {
        Log.i(TAG, "setData: model==" + model);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, "失败了", Toast.LENGTH_LONG).show();
    }
}
