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
            godList.add(new God.Builder("宙斯").about("克洛诺斯和瑞亚之子；掌管天界，是第三任神王；以贪花好色著名。").build());
            godList.add(new God.Builder("雅典娜").about("智慧女神和女战神；她是智慧，理智和纯洁的化身。").build());
            godList.add(new God.Builder("波塞冬").about("宙斯的兄弟；掌管大海；脾气暴躁，贪婪。").build());
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
    public void searchInfo(final String name) {
        if (null == name || 0 == name.length()) {
            view.showInputError("输入值为空");
        } else {
            DataSupport.where("name = ?", name).findFirstAsync(God.class).listen(new FindCallback() {
                @Override
                public <T> void onFinish(T t) {
                    String info;
                    if (null == t) {
                        info = "经查询\"" + name + "\"非希腊神话人物";
                    } else {

                        info = "经查询\"" + name + "\"是希腊神话人物";
                        info += "\n简介：";
                        info += "\n" + ((God) t).getAbout();
                    }
                    view.showInfo(info);
                }
            });
        }
    }
}
