package com.leeo.servlet;

import com.leeo.support.StringUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by lijun on 2016/3/8.
 */
@WebServlet(urlPatterns = "/index")
public class SyncIndexController extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //使用静态变量，造成数据错误
        StringUtil.setName(Thread.currentThread().getName());
        out.println(StringUtil.getName()+"进入servlet的时间：" + new Date() + ".");
        out.flush();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println(StringUtil.getName()+"结束servlet的时间：" + new Date() + ".");
        out.flush();
    }
}
