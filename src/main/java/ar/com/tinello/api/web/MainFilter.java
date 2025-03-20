package ar.com.tinello.api.web;

import java.io.IOException;

import ar.com.tinello.api.core.Provider;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;
import io.opentelemetry.context.Context;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class MainFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    final var init = System.nanoTime();

    final var provider = (Provider) req.getServletContext().getAttribute(ContextAttributes.PROVIDER.toString());
    final var tracer = provider.getTracer(req.getPathInfo());
    final var spanBuilder = tracer.spanBuilder("DoFilter");

    final var headerTraceParent = req.getHeader("traceparent");
    if (headerTraceParent != null) {
      final var traceArray = headerTraceParent.split("-");
      final var spanContext = SpanContext.createFromRemoteParent(traceArray[1], traceArray[2], TraceFlags.getSampled(), TraceState.getDefault());
      final var spanNoOp = Span.wrap(spanContext);
      spanBuilder.setParent(Context.current().with(spanNoOp));
    }

    final var span = spanBuilder.startSpan();
        
    req.setAttribute(RequestAttributes.TRACER.name(), tracer);
    req.setAttribute(RequestAttributes.SPAN_CONTEXT_PARENT.name(), span.getSpanContext());

    chain.doFilter(request, response);

    final var end = System.nanoTime() - init;
    System.out.println(req.getRequestURI() + " - " + end);

    span.end();
  }
  
}
