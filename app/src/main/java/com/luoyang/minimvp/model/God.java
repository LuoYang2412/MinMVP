package com.luoyang.minimvp.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by LuoYang on 2017/11/28.
 */
public class God extends DataSupport {
    @Column(nullable = false, unique = true)
    private final String name;
    private final String about;

    private God(Builder builder) {
        name = builder.name;
        about = builder.about;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public static final class Builder {
        private final String name;
        private String about;

        public Builder(String name) {
            this.name = name;
        }

        public Builder about(String val) {
            about = val;
            return this;
        }

        public God build() {
            return new God(this);
        }
    }
}
//Builder模式注意：
//类的构造方法是私有的，调用者不能直接创建User对象。
//类的属性都是不可变的。所有的属性都添加了final修饰符，并且在构造方法中设置了值。并且，对外只提供getters方法。
//Builder的内部类构造方法中只接收必传的参数，并且该必传的参数使用了final修饰符。