package com.luoyang.minimvp;

import com.luoyang.minimvp.model.God;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoYang on 2017/11/28.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    MainPresenter(MainContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        if (null == DataSupport.findFirst(God.class)) {
            List<God> godList = new ArrayList<>();
            godList.add(new God.Builder().name("宙斯").build());
            godList.add(new God.Builder().name("雅典娜").build());
            godList.add(new God.Builder().name("波塞冬").build());
            DataSupport.saveAllAsync(godList).listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    if (success) {
                        view.showToast("数据库部署成功");
                    } else {
                        view.showToast("数据库部署失败");
                    }
                }
            });
        } else {
            view.showToast("数据库已部署");
        }
    }

    @Override
    public void searchInfo(final String s) {
        if (null == s || 0 == s.length()) {
            view.showInputError("输入值为空");
        } else {
            DataSupport.select("name").where("name = ?", s).findFirstAsync(God.class).listen(new FindCallback() {
                @Override
                public <T> void onFinish(T t) {
                    String info;
                    if (null == t) {
                        info = "经查询\n\"" + s + "\"\n不是一位\n希腊神话人物";
                    } else {
                        info = "经查询\n\"" + s + "\"\n是一位\n希腊神话人物";
                    }
                    view.showInfo(info);
                }
            });
        }
    }
}
