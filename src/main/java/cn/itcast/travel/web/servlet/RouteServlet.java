package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();

    /**
     * 查询列表信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
//        如果通过主页进行传递 会传递字符串"null" 需要判断
        if (cid.equals("")) {
            cid = "5";
        }
        if (cid == null || "null".equals(cid) || Integer.valueOf(cid) != 5) {
            cid = "5";
        }

        String currentPage = request.getParameter("currentPage");
        if (currentPage == null || currentPage.equals("")) {
            currentPage = "1";
        }
        String displayCounts = request.getParameter("displayCounts");
        if (displayCounts == null || displayCounts.equals("")) {
            displayCounts = "5";
        }

        String rename = request.getParameter("rname");
//        获取PageBean
        RouteService service = new RouteServiceImpl();
        PageBean<Route> route = service.findRoute(
                Integer.valueOf(currentPage), Integer.valueOf(displayCounts), Integer.valueOf(cid), rename);
//        封装Json形式 响应
        setJson(response, route);
    }

    /**
     * 获取详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//          获取rid
        String rid = request.getParameter("rid");
//        通过rid查询相关信息
        RouteService service = new RouteServiceImpl();
        Route route = service.findOne(rid);
//        将route异步响应
        setJson(response, route);
    }

    /**
     * 判断是否被收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//           获取rid uid
        String rid = request.getParameter("rid");
        if (rid.equals("")) {
            return;
        }
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
//        判断是否被收藏
        boolean flag = routeService.isFavorite(Integer.valueOf(rid), uid);
        setJson(response, flag);

    }

    /**
     * 添加收藏功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//       获取rid uid
        String rid = request.getParameter("rid");
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
//            如果没有数据 则不能赋值 结束
            return;
        } else {
            uid = user.getUid();
        }
        routeService.addFavorite(Integer.valueOf(rid), uid);
    }

    /**
     * 获取我的收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavoriteByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获取uid
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
//            如果没有数据 则不能赋值 结束
            return;
        } else {
            uid = user.getUid();
        }
//        获取收藏的route集合
        List<Route> routeList = routeService.findFavoriteByUid(uid);
        if (routeList == null) {
            return;
        }
        setJson(response, routeList);

    }


}
