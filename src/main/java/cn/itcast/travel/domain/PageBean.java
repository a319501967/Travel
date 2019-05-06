package cn.itcast.travel.domain;

import java.util.List;

/**
 * 页面管理类
 * @param <T>
 */
public class PageBean<T> {
    private int currentPage; //当前页
    private int totalPages; // 总页数
    private int displayCounts; // 每页显示数量
    private int totalDisplayCounts; // 总显示数量
    private List<T> list; //保存相应集合

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", displayCounts=" + displayCounts +
                ", totalDisplayCounts=" + totalDisplayCounts +
                ", list=" + list +
                '}';
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getDisplayCounts() {
        return displayCounts;
    }

    public void setDisplayCounts(int displayCounts) {
        this.displayCounts = displayCounts;
    }

    public int getTotalDisplayCounts() {
        return totalDisplayCounts;
    }

    public void setTotalDisplayCounts(int totalDisplayCounts) {
        this.totalDisplayCounts = totalDisplayCounts;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
