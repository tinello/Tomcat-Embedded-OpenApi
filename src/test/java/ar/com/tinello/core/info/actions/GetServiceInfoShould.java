package ar.com.tinello.core.info.actions;

import org.junit.jupiter.api.Test;

import ar.com.tinello.api.core.info.actions.GetServiceInfo;
import ar.com.tinello.core.info.infrastructure.ServiceInfoRepoInMemory;
import ar.com.tinello.core.info.infrastructure.SpanContextMock;
import ar.com.tinello.core.info.infrastructure.TracerMock;

import static org.assertj.core.api.Assertions.assertThat;

public class GetServiceInfoShould {

  @Test
  void return_service_info_successful() {

    final var getServiceInfo = new GetServiceInfo(new ServiceInfoRepoInMemory());

    final var info = getServiceInfo.execute(new TracerMock(), new SpanContextMock());

    assertThat(info.getApiName()).isEqualTo("api_name");
    assertThat(info.getApiVersion()).isEqualTo("api_version");
    assertThat(info.getApiHealthy()).isTrue();
  }
}
