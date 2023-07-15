package ar.com.tinello.api.web;

import java.util.HashMap;
import java.util.Set;

import org.openapi4j.core.exception.ResolutionException;
import org.openapi4j.core.validation.ValidationException;
import org.openapi4j.operation.validator.validation.RequestValidator;
import org.openapi4j.parser.OpenApi3Parser;
import org.openapi4j.parser.model.v3.OpenApi3;

import ar.com.tinello.api.core.Provider;
import ar.com.tinello.api.web.operations.ServiceInfo;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class OnContainerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
    final var classLoader = getClass().getClassLoader();
    final var resource = classLoader.getResource("contract.yaml");

    OpenApi3 api;
    try {
      api = new OpenApi3Parser().parse(resource, false);
      
    } catch (ResolutionException | ValidationException e) {
      throw new ServerException(e);
    }

    ctx.setAttribute(ContextAttributes.REQUEST_VALIDATE.toString(), new RequestValidator(api));
    ctx.setAttribute(ContextAttributes.PROVIDER.toString(), new Provider());
    
    final var operations = new HashMap<String, Operation>();
    final var serviceInfo = new ServiceInfo();
    operations.put(serviceInfo.getId(), serviceInfo);
    ctx.setAttribute(ContextAttributes.OPERATIONS.toString(), operations);

    System.out.println("GT - Start Up");
  }
  
}
