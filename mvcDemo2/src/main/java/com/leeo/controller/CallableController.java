package com.leeo.controller;

import com.leeo.Domain.JavaBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by lijun on 2016/3/11.
 */
@Controller
public class CallableController {

    @RequestMapping("/callable/async")
    public Callable<String> callableAsync(@RequestParam(value = "latency", defaultValue = "2000") final long latency,
                                          @RequestParam(value = "blowup", defaultValue = "false")final boolean blowUp, HttpServletRequest request,
                                          final HttpServletResponse response, final Model model) {
        System.out.println("线程名称：" + Thread.currentThread().getName());
        return new Callable<String>() {
            public String call() throws Exception {
//                PrintWriter writer = null;
//                try {
//                    response.setContentType("text/html;charset=UTF-8");
//                    writer = response.getWriter();
//                    writer.println("进入controller的时间：" + new Date() + ".");
//                    writer.flush();
                    String searchResult = getSearchResult(latency, blowUp);
                    model.addAttribute("msg",searchResult);
//                    writer.println(searchResult);
//                    writer.flush();
//                    writer.println("退出controller的时间：" + new Date() + ".");
//                    writer.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (writer != null) {
//                        writer.close();
//                    }
//                }
                return "index";
            }
        };
    }

    private String getSearchResult(long latency, boolean blowUp) throws Exception {

        if (blowUp) {
            throw new RuntimeException("Bad error happened in controller");
        }

        Thread.sleep(latency);

        StringBuilder builder = new StringBuilder("Some search/whatever results being returned");
        Date now = new Date();
        builder.append(" @").append(now);
        //循环一段时间耗时，以模拟业务方法的执行，不能使用sleep方法
//        for (int i = 0; i < 20000; i++) {
//                System.out.println(Thread.currentThread().getName() + ":" + i);
//        }

        return builder.toString();
    }

    @RequestMapping(value = "/callable/async-responseBody")
    @ResponseBody
    public Callable<JavaBean> callableAsync(final HttpServletResponse response) {
        System.out.println("线程名称：" + Thread.currentThread().getName());
        return new Callable<JavaBean>() {
            public JavaBean call() throws Exception {
//                PrintWriter writer = null;
//                try {
//                    response.setContentType("text/html;charset=UTF-8");
//                    writer = response.getWriter();
//                    writer.println("进入controller的时间：" + new Date() + ".");
//                    writer.flush();
//                    writer.flush();
//                    writer.println("退出controller的时间：" + new Date() + ".");
//                    writer.flush();
//                    return new JavaBean("lijun","apple");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (writer != null) {
//                        writer.close();
//                    }
//                }
                Thread.sleep(2000);
                return new JavaBean("lijun","apple");
            }
        };
    }
}
