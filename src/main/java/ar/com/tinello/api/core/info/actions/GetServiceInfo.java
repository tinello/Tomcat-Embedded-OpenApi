package ar.com.tinello.api.core.info.actions;

import ar.com.tinello.api.core.info.domain.ServiceInfo;
import ar.com.tinello.api.core.info.domain.ServiceInfoRepo;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;

public class GetServiceInfo {
  
  private final ServiceInfoRepo repo;

  public GetServiceInfo(final ServiceInfoRepo repo) {
    this.repo = repo;
  }

  public ServiceInfo execute(Tracer tracer, SpanContext spanContextParent) {
    return repo.get(tracer, spanContextParent);
  }

}
