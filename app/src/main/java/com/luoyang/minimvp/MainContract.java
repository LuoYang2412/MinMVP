package com.luoyang.minimvp;

import com.luoyang.minimvp.base.BasePresenter;
import com.luoyang.minimvp.base.BaseView;

/** 契约类
 * Created by LuoYang on 2017/11/28.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        void searchInfo(String s);
    }

    interface View extends BaseView<Presenter> {
        void showToast(String msg);

        void showInfo(String info);

        void showInputError(String error);
    }

}
