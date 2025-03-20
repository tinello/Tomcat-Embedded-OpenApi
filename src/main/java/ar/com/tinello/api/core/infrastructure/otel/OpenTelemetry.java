package ar.com.tinello.api.core.infrastructure.otel;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

public class OpenTelemetry {
    
    private final OpenTelemetrySdk openTelemetrySdk;

    public OpenTelemetry(String serviceName, String grpcCollectorEndpoint) {
        // OpenTelemetry agent configuration
        Resource resource = Resource.create(Attributes.of(AttributeKey.stringKey("service.name"), serviceName));
        openTelemetrySdk = OpenTelemetrySdk.builder()
        .setTracerProvider(SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(OtlpGrpcSpanExporter.builder()
            .setEndpoint(grpcCollectorEndpoint)
            .build()))
            .setResource(resource)
            .build())
        .build();
    }

    public Tracer getTracer(String instrumentationScopeName) {
        return openTelemetrySdk.getTracer(instrumentationScopeName);
    }

}
