package ar.com.tinello.core.info.infrastructure;

import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;

public class SpanContextMock implements SpanContext {

    @Override
    public String getTraceId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTraceId'");
    }

    @Override
    public String getSpanId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpanId'");
    }

    @Override
    public TraceFlags getTraceFlags() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTraceFlags'");
    }

    @Override
    public TraceState getTraceState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTraceState'");
    }

    @Override
    public boolean isRemote() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isRemote'");
    }
    
}
