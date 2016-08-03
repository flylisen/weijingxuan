package com.lisen.android.weijingxuan.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public class ResponseBody {
    private int ret_code;
    private PageBean pagebean;

    public int getRet_code() {
        return ret_code;
    }

    public PageBean getPagebean() {
        return pagebean;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "ret_code=" + ret_code +
                ", pagebean=" + pagebean +
                '}';
    }
}
