package ar.com.tinello.api.core;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.com.tinello.api.core.info.actions.GetServiceInfo;
import ar.com.tinello.api.core.info.infrastructure.ServiceInfoRepoPostgres;
import ar.com.tinello.api.core.infrastructure.sql.SqlClient;

public class Provider {

  private final GetServiceInfo getServiceInfo;
  
  public Provider(Environment environment) {
    final var ds = getDataSource(environment.getDbUrl(), environment.getDbUser(), environment.getDbPass());
    final var sqlClient = new SqlClient(ds);
    getServiceInfo = new GetServiceInfo(new ServiceInfoRepoPostgres(sqlClient));
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
