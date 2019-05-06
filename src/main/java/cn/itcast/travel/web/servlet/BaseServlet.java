package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 *  各类的集成类 获取相应的方法 通过反射运行
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       获取uri
        String uri = req.getRequestURI();
//        System.out.println(uri);//   /travel/user/add
//        切割字符串 获取方法名
        String newUri = uri.substring(uri.lastIndexOf("/") + 1);
//        System.out.println(newUri);

//        谁调用BaseServlet方法 this就是谁
        try {
            Method method = this.getClass().getMethod(newUri, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    protected void setJson(HttpServletResponse resp,Object obj) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(resp.getOutputStream(),obj);
    }
}
