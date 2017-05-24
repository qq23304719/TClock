package com.jkt.clock;

/**
 * Created by 天哥哥 at 2017/4/26 20:27
 */
public class TypeBean {
    private String name;
    private Class<?> tClass;

    public void setName(String name) {
        this.name = name;
    }

    public void settClass(Class<?> tClass) {
        this.tClass = tClass;
    }

    public String getName() {
        return name;
    }

    public Class<?> gettClass() {
        return tClass;
    }

    public TypeBean(String name, Class<?> tClass) {
        this.name = name;
        this.tClass = tClass;
    }
}
