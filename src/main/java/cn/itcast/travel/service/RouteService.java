package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    /**
     * 根据当前页 每页显示数量 查询显示信息
     * @param currentPage
     * @param displayCounts
     * @param cid
     * @param rename
     * @return
     */
    PageBean<Route> findRoute(int currentPage, int displayCounts, int cid, String rename);

    /**
     * 查询详细信息
     * @param rid
     * @return
     */
    Route findOne(String rid);

    /**
     * 获取rid是否被uid收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);

    /**
     * 获取我的收藏
     * @param uid
     * @return
     */
    List<Route> findFavoriteByUid(int uid);
}
