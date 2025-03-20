package ar.com.tinello.api.core.info.infrastructure;

import ar.com.tinello.api.core.info.domain.ServiceInfo;
import ar.com.tinello.api.core.info.domain.ServiceInfoRepo;
import ar.com.tinello.api.core.infrastructure.sql.SqlClient;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

public class ServiceInfoRepoPostgres implements ServiceInfoRepo {

private final SqlClient sqlClient;

  public ServiceInfoRepoPostgres(SqlClient sqlClient) {
    this.sqlClient = sqlClient;
  }

  @Override
  public ServiceInfo get(Tracer tracer, SpanContext spanContextParent) {
    final var span = tracer.spanBuilder("ServiceInfo Repo")
      .setParent(Context.current().with(Span.wrap(spanContextParent)))
      .startSpan();

    final var query = "Select ?, ?, ?";

    span.setAttribute("query", query);

    final var table = sqlClient.query(query, "API", "0.0.1", true);

    final var row = table.get(0);
    span.end();
    return new ServiceInfo(row.get(0).getString(), row.get(1).getString(), row.get(2).getBoolean());
  }
  
}
