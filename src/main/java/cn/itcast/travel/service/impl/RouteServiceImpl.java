package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private PageBean<Route> routePageBean = new PageBean<>();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 根据当前页 每页显示数量 查询显示信息
     *
     * @param currentPage
     * @param displayCounts
     * @param cid
     * @param rename
     * @return
     */
    @Override
    public PageBean<Route> findRoute(int currentPage, int displayCounts, int cid, String rename) {
//        查询每页起始数据
        int startDisplay;// 每页起始数据
//        (当前页-1)*每页显示数据数
        startDisplay = (currentPage - 1) * displayCounts;
//        查询数据
        List<Route> routeList = routeDao.findRoute(startDisplay, displayCounts, cid, rename);
//        获取总数据数
        int totalDisplayCounts = routeDao.findTotalDisplayCounts(cid, rename);
//        获取总页数
        int totalPages = totalDisplayCounts % displayCounts == 0 ?
                totalDisplayCounts / displayCounts : (totalDisplayCounts / displayCounts) + 1;
//        封装数据
        routePageBean.setList(routeList);
        routePageBean.setTotalDisplayCounts(totalDisplayCounts);
        routePageBean.setCurrentPage(currentPage);
        routePageBean.setDisplayCounts(displayCounts);
        routePageBean.setTotalPages(totalPages);
        return routePageBean;
    }

    /**
     * 查询详细信息
     *
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        int intRid = Integer.valueOf(rid);
//        通过rid查询出来不完整route 在通过查询出来的rid sid 查询图片和销售信息 封装到route中
        Route route = routeDao.findOne(intRid);
//      封装图片
        RouteImgDao routeimgDao = new RouteImgDaoImpl();
        List<RouteImg> routeImgList = routeimgDao.findImgByRid(intRid);
        route.setRouteImgList(routeImgList);
//        封装销售信息
        SellerDao sellerDao = new SellerDaoImpl();
        Seller seller = sellerDao.findSellerBySid(route.getSid());
        route.setSeller(seller);
//        查询被收藏的次数
        int count = favoriteDao.findFavoriteCountsByRid(intRid);
        route.setCount(count);
        return route;
    }

    /**
     * 获取rid是否被uid收藏
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.isFavorite(rid, uid);
        if (favorite == null) {
            return false;

        } else {
            return true;
        }
    }

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     */

    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.addFavorite(rid, uid);
    }

    @Override
    public List<Route> findFavoriteByUid(int uid) {
        List<Route> routeList = null;
//        获取所有 用户的对应的List(rid)
        List<Integer> ridList = favoriteDao.findCidByUid(uid);
//      如果查询出来为空 则返回空集合
        if (ridList == null) {
            return routeList;

        }//      通过rid的List形式查找 相关List(Route)

        routeList = routeDao.findRouteByRid(ridList);
        return routeList;
    }
}

