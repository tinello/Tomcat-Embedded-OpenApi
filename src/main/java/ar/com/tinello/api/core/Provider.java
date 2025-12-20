package ar.com.tinello.api.core;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.com.tinello.api.core.info.actions.GetServiceInfo;
import ar.com.tinello.api.core.info.infrastructure.ServiceInfoRepoPostgres;
import ar.com.tinello.api.core.infrastructure.otel.OpenTelemetry;
import ar.com.tinello.api.core.infrastructure.sql.SqlClient;
import io.opentelemetry.api.trace.Tracer;

public class Provider {

  private final OpenTelemetry openTelemetry;
  private final GetServiceInfo getServiceInfo;
  private final Environment environment;
  
  public Provider(Environment environment) {
    this.environment = environment;
    openTelemetry = new OpenTelemetry(environment.getOtelServiceName(), environment.getOtelGrpcEndpoint());

    final var ds = getDataSource(environment.getDbUrl(), environment.getDbUser(), environment.getDbPass());
    final var sqlClient = new SqlClient(ds);
    getServiceInfo = new GetServiceInfo(new ServiceInfoRepoPostgres(sqlClient));
  }
  
    private DataSource getDataSource(String dbUrl, String dbUser, String dbPass) {
    final var config = new HikariConfig();
    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl("jdbc:postgresql://" + dbUrl);
    config.setUsername(dbUser);
    config.setPassword(dbPass);
    config.setMaximumPoolSize(20);
    config.setMinimumIdle(1);
    
    return new HikariDataSource(config);
  }

  public GetServiceInfo getServiceInfo() {
    return getServiceInfo;
  }

  public Tracer getTracer(String instrumentationScopeName) {
    return openTelemetry.getTracer(instrumentationScopeName);
  }

  public String getEnv(String name, String defaultValue) {
    return environment.getEnv(name, defaultValue);
  }
}
