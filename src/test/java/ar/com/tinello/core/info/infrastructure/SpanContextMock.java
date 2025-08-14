package ar.com.tinello.core.info.infrastructure;

import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;

public class SpanContextMock implements SpanContext {

    @Override
    public String getTraceId() {
        throw new UnsupportedOperationException("Unimplemented method 'getTraceId'");
    }

    @Override
    public String getSpanId() {
        throw new UnsupportedOperationException("Unimplemented method 'getSpanId'");
    }

    @Override
    public TraceFlags getTraceFlags() {
        throw new UnsupportedOperationException("Unimplemented method 'getTraceFlags'");
    }

    @Override
    public TraceState getTraceState() {
        throw new UnsupportedOperationException("Unimplemented method 'getTraceState'");
    }

    @Override
    public boolean isRemote() {
        throw new UnsupportedOperationException("Unimplemented method 'isRemote'");
    }
    
}
