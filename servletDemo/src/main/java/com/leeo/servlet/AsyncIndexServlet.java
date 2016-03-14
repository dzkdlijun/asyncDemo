package com.leeo.servlet;

import com.leeo.support.Executor;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by lijun on 2016/3/8.
 * 异步处理测试
 */
@WebServlet(urlPatterns = "/async/index",asyncSupported = true)
public class AsyncIndexServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(Thread.currentThread().getName()+"进入servlet的时间：" + new Date() + ".");
        out.flush();
        System.out.println(Thread.currentThread().getName()+"进入Servlet的时间：" + new Date() + ".");
        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        AsyncContext actx = request.startAsync();
        //设置异步调用的超时时长
        actx.setTimeout(100 * 1000);
        //启动异步调用的线程
        actx.start(new Executor(actx));
        System.out.println(Thread.currentThread().getName()+"结束Servlet的时间：" + new Date() + ".");
        out.println(Thread.currentThread().getName()+"结束servlet的时间：" + new Date() + ".");
        out.flush();
    }

}
