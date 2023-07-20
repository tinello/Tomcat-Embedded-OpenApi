package ar.com.tinello.core.info.infrastructure;

import ar.com.tinello.api.core.info.domain.ServiceInfo;
import ar.com.tinello.api.core.info.domain.ServiceInfoRepo;

public class ServiceInfoRepoInMemory implements ServiceInfoRepo {

  @Override
  public ServiceInfo get() {
    return new ServiceInfo("api_name", "api_version", true);
  }
  
}
