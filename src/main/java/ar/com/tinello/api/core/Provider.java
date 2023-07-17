package ar.com.tinello.api.core;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.com.tinello.api.core.actions.GetServiceInfo;

public class Provider {

  private final DataSource ds;
  private final GetServiceInfo getServiceInfo;

  public Provider() {
    ds = getDataSource();
    getServiceInfo = new GetServiceInfo(ds);
  }
  
  public GetServiceInfo getServiceInfo() {
    return getServiceInfo;
  }

  private DataSource getDataSource() {
    final var config = new HikariConfig();
    config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
    config.addDataSourceProperty("serverName", "localhost");
    config.addDataSourceProperty("portNumber", "5433");
    config.addDataSourceProperty("databaseName", "hcv_sso");
    config.addDataSourceProperty("user", "sso");
    config.addDataSourceProperty("password", "postgres");

    return new HikariDataSource(config);
  }
}
