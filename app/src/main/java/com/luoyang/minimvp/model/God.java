package com.luoyang.minimvp.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by LuoYang on 2017/11/28.
 */
public class God extends DataSupport {
    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public God setName(String name) {
        this.name = name;
        return this;
    }
}
