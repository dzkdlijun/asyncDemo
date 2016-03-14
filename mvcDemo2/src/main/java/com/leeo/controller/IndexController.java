package com.leeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by lijun on 2016/3/8.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String doSearchAjax(Model model) throws Exception {
            String searchResult = getSearchResult(2000, false);
        model.addAttribute("msg",searchResult);
        return "index";
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
