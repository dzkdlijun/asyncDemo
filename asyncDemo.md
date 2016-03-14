## 几种实现请求异步处理的方法

### servlet3.0 api（参考servletDemo）
 servlet3.0增加了异步支持，具体配置如下（这里只介绍注解配置）
 - web.xml配置，需要指定web-app版本为3.0，这样才能开启async支持
 - 在servlet上增加@WebServlet(urlPatterns = "/async/index",asyncSupported = true)配置

### springmvc重写DispatcherServlet,使用servlet3.0api,实现异步处理（参考mvcDemo）
 - web.xml配置，需要指定web-app版本为3.0，这样才能开启async支持
 - 使用AsyncDispatcherServlet继承DispatcherServlet,在servlet上增加@WebServlet(urlPatterns = "/async/index",asyncSupported = true)配置
 - 重写init(ServletConfig config)、destroy()、doDispatch(final HttpServletRequest request, final HttpServletResponse response)方法，
 增加ThreadPoolExecutor，将所有接收到的请求放到执行器中调用DispatcherServlet的doDispatch()方法处理。  
 - 在web.xml中配置AsyncDispatcherServlet，并加上`<async-supported>true</async-supported>`

### springmvc 使用Callable实现异步(参考mvcDemo2)
 - web.xml配置，需要指定web-app版本为3.0，这样才能开启async支持
 - 在web.xml中配置DispatcherServlet，并加上`<async-supported>true</async-supported>`
 - 在dispatch-servlet.xml中配置ThreadPoolTaskExecutor，用其替换默认的执行器

### springmvc 使用DeferredResult实现异步
 - DeferredResult 方式可以跟JMS或者消息队列合用，实现如长轮询功能，但往往需要发起二次请求，具体可以参考http://ju.outofmemory.cn/entry/28814、http://www.tuicool.com/articles/bi226f
