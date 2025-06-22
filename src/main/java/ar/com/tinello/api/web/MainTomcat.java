package ar.com.tinello.api.web;

import java.io.File;
import java.util.concurrent.Executors;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

public class MainTomcat {

  private MainTomcat(){}
  
  public static void start() {
    final var tomcat = new Tomcat();
    tomcat.setBaseDir("temp");
    final var conn = new Connector();
    conn.setPort(8080);
    conn.setProperty("executor", "virtualThreadExecutor");
    conn.getProtocolHandler().setExecutor(Executors.newVirtualThreadPerTaskExecutor());

    tomcat.setConnector(conn);

    final var contextPath = "/";
    final var docBase = new File(".").getAbsolutePath();
    final var ctx = tomcat.addContext(contextPath, docBase);

    var manager = ctx.getManager();

    if (manager == null) {
      manager = new StandardManager();
      ctx.setManager(manager);
    }

    if (manager instanceof StandardManager) {
      ((StandardManager) manager).setPathname("");
    }

    final var servletName = "mainServlet";
    Tomcat.addServlet(ctx, servletName, new MainServlet());
    ctx.addServletMappingDecoded("/*", servletName);

    ctx.addServletContainerInitializer(new OnContainerInitializer(), null);
    
    final var fd = new FilterDef();
    fd.setFilterName(MainFilter.class.getSimpleName());
    fd.setFilterClass(MainFilter.class.getName());
    ctx.addFilterDef(fd);

    final var fm = new FilterMap();
    fm.setFilterName(MainFilter.class.getSimpleName());
    fm.addURLPattern("/*");
    ctx.addFilterMap(fm);

    try {
      tomcat.start();
    } catch (LifecycleException e) {
      throw new ServerException(e);
    }
    tomcat.getServer().await();
  }

}
