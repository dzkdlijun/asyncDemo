package com.leeo.support;
import javax.servlet.AsyncContext;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by lijun on 2016/3/8.
 */
public class Executor implements Runnable {
    private AsyncContext ctx = null;
    public Executor(AsyncContext ctx){
        this.ctx = ctx;
    }

    public void run(){
        try {
            //循环一段时间耗时，以模拟业务方法的执行，不能使用sleep方法
//            for(int i=0;i<500000;i++){
//                System.out.println(Thread.currentThread().getName()+":" +i);
//            }
            //使用thread.sleep()方式
            Thread.sleep(15*1000);
            PrintWriter out = ctx.getResponse().getWriter();
            out.println("业务处理完毕的时间：" + new Date() + ".");
            out.flush();
            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
