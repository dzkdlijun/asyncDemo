## ����ʵ�������첽����ķ���

### servlet3.0 api���ο�servletDemo��
 servlet3.0�������첽֧�֣������������£�����ֻ����ע�����ã�
 - web.xml���ã���Ҫָ��web-app�汾Ϊ3.0���������ܿ���async֧��
 - ��servlet������@WebServlet(urlPatterns = "/async/index",asyncSupported = true)����

### springmvc��дDispatcherServlet,ʹ��servlet3.0api,ʵ���첽�����ο�mvcDemo��
 - web.xml���ã���Ҫָ��web-app�汾Ϊ3.0���������ܿ���async֧��
 - ʹ��AsyncDispatcherServlet�̳�DispatcherServlet,��servlet������@WebServlet(urlPatterns = "/async/index",asyncSupported = true)����
 - ��дinit(ServletConfig config)��destroy()��doDispatch(final HttpServletRequest request, final HttpServletResponse response)������
 ����ThreadPoolExecutor�������н��յ�������ŵ�ִ�����е���DispatcherServlet��doDispatch()��������  
 - ��web.xml������AsyncDispatcherServlet��������`<async-supported>true</async-supported>`

### springmvc ʹ��Callableʵ���첽(�ο�mvcDemo2)
 - web.xml���ã���Ҫָ��web-app�汾Ϊ3.0���������ܿ���async֧��
 - ��web.xml������DispatcherServlet��������`<async-supported>true</async-supported>`
 - ��dispatch-servlet.xml������ThreadPoolTaskExecutor�������滻Ĭ�ϵ�ִ����

### springmvc ʹ��DeferredResultʵ���첽
 - DeferredResult ��ʽ���Ը�JMS������Ϣ���к��ã�ʵ���糤��ѯ���ܣ���������Ҫ����������󣬾�����Բο�http://ju.outofmemory.cn/entry/28814��http://www.tuicool.com/articles/bi226f
