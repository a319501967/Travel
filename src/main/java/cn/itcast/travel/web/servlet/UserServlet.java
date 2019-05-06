package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //      验证码是否正确
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
//        保证验证码使用一次后会销毁
        session.removeAttribute("CHECKCODE_SERVER");
        String checkcode = request.getParameter("check");
        ResultInfo info = new ResultInfo();
        System.out.println(checkcode);
        if (checkcode == null || checkcode.equals("") || !checkcode.equalsIgnoreCase(checkcode_server)) {
//            验证码错误 封装错误信息并发送

            info.setFlag(false);
            info.setErrorMsg("验证码错误");
//            将info 序列化转换成Json进行传输
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            return;
        } else {
            User user = new User();
            //       获取注册信息
            Map<String, String[]> map = request.getParameterMap();
//            使用BeanUnits进行封装
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
//            判断用户名
            Boolean flog = userService.findByUser(user.getUsername(), user);
//          flog为true则注册成功 如果为false则用户名重复
            if (flog) {
                info.setFlag(true);
                ObjectMapper mapper = new ObjectMapper();
                String s = mapper.writeValueAsString(info);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(s);
            } else {
                info.setFlag(false);
                info.setErrorMsg("用户名太火爆了 换一个吧！");
                ObjectMapper mapper = new ObjectMapper();
                String s = mapper.writeValueAsString(info);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(s);
            }

        }
    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //         创建消息封装对象
        ResultInfo info = new ResultInfo();
        User user = new User();

//     获取验证码进行比较
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        if (check == null || check.equals("") || !checkcode_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误呦");
            sendJson(response, info);
        } else {
//            如果验证码正确 校验用户名密码
//            获取用户名密码
            Map<String, String[]> map = request.getParameterMap();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            user = userService.findUserNameAndPassWord(user.getUsername(), user.getPassword());

            if (user == null) {
//              如果没有该信息
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
                sendJson(response, info);
            } else {
//              如果查询到消息 判断 用户的注册状态
                if (!"Y".equals(user.getStatus())) {
                    info.setFlag(false);
                    info.setErrorMsg("您还没有激活呦 ！去激活吧");
                    sendJson(response, info);
                } else {
//                  如果激活 登录成功
                    request.getSession().setAttribute("user", user);
                    info.setFlag(true);
                    sendJson(response, info);
                }
            }
        }

    }

    /**
     * 获取已登录用户姓名
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");

            new ObjectMapper().writeValue(response.getWriter(), user);


    }

    /**
     * 退出
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");

    }

    public void activate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //   获取激活码
        UserService userService = new UserServiceImpl();
        String code = request.getParameter("code");
        Boolean flog = userService.findCode(code);
        if (flog) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("激活成功！！点击<a href=''>登录</a>");
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("别乱搞");
        }


    }


    /**
     * 将封装消息转换为Json并响应
     *
     * @param response
     * @param info
     * @throws IOException
     */
    private void sendJson(HttpServletResponse response, ResultInfo info) throws IOException {
        response.setContentType("application/json;charset=utf-8");
//            转换为Json 响应
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), info);
    }
}
