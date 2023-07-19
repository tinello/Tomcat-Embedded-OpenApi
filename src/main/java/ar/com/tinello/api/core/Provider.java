package ar.com.tinello.api.core;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.com.tinello.api.core.actions.GetServiceInfo;

public class Provider {

  private final DataSource ds;
  private final GetServiceInfo getServiceInfo;
  
  public Provider(Environment environment) {
    ds = getDataSource(environment.getDbUrl(), environment.getDbUser(), environment.getDbPass());
    getServiceInfo = new GetServiceInfo(ds);
  }
  
  public GetServiceInfo getServiceInfo() {
    return getServiceInfo;
  }

  private DataSource getDataSource(String dbUrl, String dbUser, String dbPass) {
    final var config = new HikariConfig();
    config.setDriverClassName("org.postgresql.ds.PGSimpleDataSource");
    config.setJdbcUrl("jdbc:postgresql://" + dbUrl);
    config.setUsername(dbUser);
    config.setPassword(dbPass);
    config.setMaximumPoolSize(20);
    config.setMinimumIdle(1);
    
    return new HikariDataSource(config);
  }
}
