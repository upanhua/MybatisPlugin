package com.leyou.item.util;

/**
 * @author lh
 * @version 1.0
 * @date 2019-09-25 17:32
 */
public class PageUtil {

    /**
     * 页数
     */
    int page;

    /**
     * 限制数
     */
    int limit;

    /**
     * 总数
     */
    int count;

    /**
     * 开始数
     */
    int start;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return (page-1)*limit;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
