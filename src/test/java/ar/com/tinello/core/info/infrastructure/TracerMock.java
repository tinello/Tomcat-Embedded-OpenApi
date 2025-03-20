package ar.com.tinello.core.info.infrastructure;

import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;

public class TracerMock implements Tracer  {

    @Override
    public SpanBuilder spanBuilder(String spanName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'spanBuilder'");
    }
    
}
