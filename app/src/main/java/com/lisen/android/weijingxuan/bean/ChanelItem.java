package com.lisen.android.weijingxuan.bean;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ChanelItem {
    public String id;
    public String name;

    public ChanelItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
