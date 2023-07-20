package ar.com.tinello.api.core.info.infrastructure;

import ar.com.tinello.api.core.info.domain.ServiceInfo;
import ar.com.tinello.api.core.info.domain.ServiceInfoRepo;
import ar.com.tinello.api.core.infrastructure.sql.SqlClient;

public class ServiceInfoRepoPostgres implements ServiceInfoRepo {

private final SqlClient sqlClient;

  public ServiceInfoRepoPostgres(SqlClient sqlClient) {
    this.sqlClient = sqlClient;
  }

  @Override
  public ServiceInfo get() {
    final var table = sqlClient.query("Select ?, ?, ?", "API", "0.0.1", true);

    final var row = table.get(0);
    return new ServiceInfo(row.get(0).getString(), row.get(1).getString(), row.get(2).getBoolean());
  }
  
}
