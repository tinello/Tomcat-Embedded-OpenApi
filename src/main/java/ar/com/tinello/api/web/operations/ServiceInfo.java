package ar.com.tinello.api.web.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.tinello.api.core.Provider;
import ar.com.tinello.api.web.Operation;
import ar.com.tinello.api.web.ServerException;
import jakarta.servlet.http.HttpServletRequest;

public class ServiceInfo implements Operation {

  @Override
  public String getId() {
    return "service_info";
  }

  @Override
  public String execute(HttpServletRequest req, Provider provider) {

    ObjectMapper objectMapper = new ObjectMapper();

    /*
    final var obj = objectMapper.createObjectNode();
    final var arr = objectMapper.createArrayNode();
    arr.add(objectMapper.createObjectNode().put("a", "aa").put("b", "bb"));
    
    obj.set("c", arr);
    obj.put("d", "dd");
    */


    final var obj = objectMapper.createObjectNode();
    
    
    try {

      final var response = provider.getServiceInfo().execute();

      obj.put("name", response.getApiName());
      obj.put("version", response.getApiVersion());
      obj.put("healthy", response.getApiHealthy());

      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new ServerException(e);
    }
  }
  
}
