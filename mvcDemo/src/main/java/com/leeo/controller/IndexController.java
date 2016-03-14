package com.leeo.controller;

import com.leeo.support.JavaBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by lijun on 2016/3/8.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public void doSearchAjax(@RequestParam(value = "latency", defaultValue = "2000") long latency,
                             @RequestParam(value = "blowup", defaultValue = "false") boolean blowUp,
                             HttpServletResponse response) throws Exception {
        PrintWriter writer = null;
        response.setContentType("text/html;charset=UTF-8");
        try {
            writer = response.getWriter();
            writer.println("进入controller的时间：" + new Date() + ".");
            writer.flush();
            String searchResult = getSearchResult(latency, blowUp);
            writer.println(searchResult);
            writer.flush();
            writer.println("退出controller的时间：" + new Date() + ".");
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @RequestMapping("/index-view")
    public String doSearchAjax(@RequestParam(value = "latency", defaultValue = "2000") long latency,
                             @RequestParam(value = "blowup", defaultValue = "false") boolean blowUp,
                             Model model) throws Exception {
            String searchResult = getSearchResult(latency, blowUp);
        model.addAttribute("msg", searchResult);
        return "index";
    }

    @RequestMapping("/index-responseBody")
    @ResponseBody
    public JavaBean doSearchAjax(@RequestParam(value = "latency", defaultValue = "2000") long latency,
                               @RequestParam(value = "blowup", defaultValue = "false") boolean blowUp) throws Exception {
        String searchResult = getSearchResult(latency, blowUp);
        return new JavaBean("lijun",searchResult);
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
}
