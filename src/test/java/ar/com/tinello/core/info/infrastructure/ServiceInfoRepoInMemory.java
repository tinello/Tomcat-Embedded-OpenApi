package ar.com.tinello.core.info.infrastructure;

import ar.com.tinello.api.core.info.domain.ServiceInfo;
import ar.com.tinello.api.core.info.domain.ServiceInfoRepo;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;

public class ServiceInfoRepoInMemory implements ServiceInfoRepo {

  @Override
  public ServiceInfo get(Tracer tracer, SpanContext spanContextParent) {
    return new ServiceInfo("api_name", "api_version", true);
  }
  
}
