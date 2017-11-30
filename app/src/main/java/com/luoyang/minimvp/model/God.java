package com.luoyang.minimvp.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by LuoYang on 2017/11/28.
 */
public class God extends DataSupport {
    @Column(nullable = false, unique = true)
    private String name;

    private God(Builder builder) {
        name = builder.name;
    }

    public static final class Builder {
        private String name;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public God build() {
            return new God(this);
        }
    }
}
