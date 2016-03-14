package leeo.support;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by lijun on 2016/3/8.
 */
public class SyncExecutor implements Runnable{
    private HttpServletResponse response = null;
    public SyncExecutor(HttpServletResponse response){
        this.response = response;
    }

    public void run(){
        try {
            //循环一段时间耗时，以模拟业务方法的执行，不能使用sleep方法
//            for(int i=0;i<500000;i++){
//                System.out.println(Thread.currentThread().getName()+":" +i);
//            }
            //使用thread.sleep()方式
            Thread.sleep(1*1000);
            PrintWriter out = response.getWriter();
            out.println("业务处理完毕的时间：" + new Date().getTime() + ".");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
