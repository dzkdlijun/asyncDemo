### servlet 异步执行顺序

 尽量业务逻辑代码在新线程中处理，不要在servlet主线程处理
- 客户端发送一个请求
- Servlet容器分配一个线程来处理容器中的一个servlet
- servlet调用request.startAsync()，保存AsyncContext, 然后返回
- 任何方式存在的容器线程都将退出，但是response仍然保持开放
- 其他线程使用保存的AsyncContext来完成响应
- 客户端收到响应

###参考资料
- SPRING MVC 3.2 预览:介绍SERVLET 3,异步支持http://www.oschina.net/translate/spring-mvc-3-2-preview-introducing-servlet-3-async-support
- servlet 线程安全性问题 http://www.cnblogs.com/gw811/archive/2012/09/07/2674859.html