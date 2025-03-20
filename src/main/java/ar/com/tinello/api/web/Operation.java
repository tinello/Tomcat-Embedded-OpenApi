package ar.com.tinello.api.web;

import ar.com.tinello.api.core.Provider;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;
import jakarta.servlet.http.HttpServletRequest;

public interface Operation {

  String getId();

  String execute(HttpServletRequest req, Provider provider, Tracer tracer, SpanContext spanContextParent);
}
