package com.luoyang.minimvp.base;

import android.view.View;

/**
 * Created by LuoYang on 2017/11/28.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void initView();

    void onClick(View view);
}
