package ar.com.tinello.api.web.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.tinello.api.core.Provider;
import ar.com.tinello.api.web.Operation;
import ar.com.tinello.api.web.ServerException;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;
import jakarta.servlet.http.HttpServletRequest;

public class ServiceInfo implements Operation {

  @Override
  public String getId() {
    return "service_info";
  }

  @Override
  public String execute(HttpServletRequest req, Provider provider, Tracer tracer, SpanContext spanContextParent) {

    final var objectMapper = new ObjectMapper();
    final var obj = objectMapper.createObjectNode();
    
    
    try {

      final var response = provider.getServiceInfo().execute(tracer, spanContextParent);

      obj.put("name", response.getApiName());
      obj.put("version", response.getApiVersion());
      obj.put("healthy", response.getApiHealthy());

      boolean isVirtual = Thread.currentThread().isVirtual();
      obj.put("isVirtual", isVirtual);


      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new ServerException(e);
    }
  }
  
}
