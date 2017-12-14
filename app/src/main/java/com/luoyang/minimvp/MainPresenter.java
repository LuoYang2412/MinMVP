package com.luoyang.minimvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.luoyang.minimvp.model.God;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * P
 * Created by LuoYang on 2017/11/28.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    MainPresenter(MainContract.View view) {
        this.view = view;
        mCompositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadDb();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    private void loadDb() {
        final Boolean[] loadDbSuccess = {true};
        Observable<Object> isHaveDb = Observable.create((e) -> {
                    God god = DataSupport.findFirst(God.class);
                    e.onNext(null == god ? "-1" : god);
                }
        );
        mCompositeDisposable.add(isHaveDb
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (!"-1".equals(o.toString())) {
                        view.showToast("数据库已部署");
                    } else {
                        mCompositeDisposable.add(getInitDbFlowable()
                                .subscribeOn(Schedulers.io())
                                .doOnSubscribe(subscription -> view.showToast("初始化数据库"))
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .distinct()
                                .subscribe(aBoolean -> loadDbSuccess[0] = loadDbSuccess[0] && aBoolean, throwable -> {
                                }, () -> {
                                    if (loadDbSuccess[0]) {
                                        view.showToast("数据库部署成功");
                                    } else {
                                        view.showToast("数据库部署失败");
                                    }
                                }));
                    }
                }));
    }

    private Flowable<Boolean> getInitDbFlowable() {
        List<God> godList = new ArrayList<>();
        godList.add(new God.Builder("宙斯").about("克洛诺斯和瑞亚之子；掌管天界，是第三任神王；以贪花好色著名。").build());
        godList.add(new God.Builder("雅典娜").about("智慧女神和女战神；她是智慧，理智和纯洁的化身。").build());
        godList.add(new God.Builder("波塞冬").about("宙斯的兄弟；掌管大海；脾气暴躁，贪婪。").build());
        return Flowable.just(godList)
                .flatMap(Flowable::fromIterable)
                .map(DataSupport::save)
                .delay(3000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void searchInfo(final String name) {
        if (null == name || 0 == name.length()) {
            view.showInputError("输入值为空");
        } else {
            mCompositeDisposable.add(Observable.just(name)
                    .map(s -> {
                        God god = DataSupport.where("name = ?", s).findFirst(God.class);
                        return null == god ? "-1" : god;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        Log.d("ly", "accept: se");
                        String info;
                        if ("-1".equals(o.toString())) {
                            info = "经查询\"" + name + "\"非希腊神话人物";
                        } else {
                            info = "经查询\"" + name + "\"是希腊神话人物";
                            info += "\n简介：";
                            info += "\n" + ((God) o).getAbout();
                        }
                        view.showInfo(info);
                    }));
        }
    }
}
