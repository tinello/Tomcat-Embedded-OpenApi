package ar.com.tinello.api.core.info.domain;

import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;

public interface ServiceInfoRepo {
  public ServiceInfo get(Tracer tracer, SpanContext spanContextParent);
}
