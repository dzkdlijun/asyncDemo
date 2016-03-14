package com.leeo.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by lijun on 2016/3/8.
 * extend Spring’s DispatcherServlet
 */
@WebServlet(urlPatterns = {"/async/*"}, asyncSupported = true, name = "async")
public class AsyncDispatcherServlet extends DispatcherServlet {
    private ExecutorService exececutor;
    private static final int NUM_ASYNC_TASKS = 15;
    private static final long TIME_OUT = 10 * 1000;
    private final Log log = LogFactory.getLog(AsyncDispatcherServlet.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        exececutor = Executors.newFixedThreadPool(NUM_ASYNC_TASKS);
    }

    @Override
    public void destroy() {
        exececutor.shutdownNow();
        super.destroy();
    }

    @Override
    protected void doDispatch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final AsyncContext ac = request.startAsync(request, response);
        ac.setTimeout(TIME_OUT);
        FutureTask task = new FutureTask(new Runnable() {

            public void run() {
                try {
                    log.debug("Dispatching request " + request);
                    AsyncDispatcherServlet.super.doDispatch(request,response );
                    log.debug("doDispatch returned from processing request " + request);
                    ac.complete();
                } catch (Exception ex) {
                    log.error("Error in async request", ex);
                }
            }
        }, null);

        ac.addListener(new AsyncDispatcherServletListener(task));
        exececutor.execute(task);
    }

    private class AsyncDispatcherServletListener implements AsyncListener {

        private FutureTask futureTask;

        public AsyncDispatcherServletListener(FutureTask futureTask) {
            this.futureTask = futureTask;
        }

        public void onTimeout(AsyncEvent event) throws IOException {
            log.warn("Async request did not complete timeout occured");
            handleTimeoutOrError(event, "Request timed out");
        }

        public void onComplete(AsyncEvent event) throws IOException {
            log.debug("Completed async request");
        }

        public void onError(AsyncEvent event) throws IOException {
            log.error("Error in async request", event.getThrowable());
            handleTimeoutOrError(event, "Error processing " + event.getThrowable().getMessage());
        }

        public void onStartAsync(AsyncEvent event) throws IOException {
            log.debug("Async Event started..");
        }

        private void handleTimeoutOrError(AsyncEvent event, String message) {
            PrintWriter writer = null;
            try {
                futureTask.cancel(true);
                HttpServletResponse response = (HttpServletResponse) event.getAsyncContext().getResponse();
                //HttpServletRequest request = (HttpServletRequest) event.getAsyncContext().getRequest();
                //request.getRequestDispatcher("/app/error.htm").forward(request, response);
                writer = response.getWriter();
                writer.print(message);
                writer.flush();
            } catch (IOException ex) {
                log.error(ex);
            } finally {
                event.getAsyncContext().complete();
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
}
