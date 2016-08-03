package com.lisen.android.weijingxuan.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public class PageBean {

    private int allPages;
    private List<Content> contentlist;
    private int currentPage;
    private int allNum;
    private int maxResult;

    public int getAllPages() {
        return allPages;
    }

    public List<Content> getContentlist() {
        return contentlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public void setContentlist(List<Content> contentlist) {
        this.contentlist = contentlist;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "allPages=" + allPages +
                ", contentlist=" + contentlist +
                ", currentPage=" + currentPage +
                ", allNum=" + allNum +
                ", maxResult=" + maxResult +
                '}';
    }
}
