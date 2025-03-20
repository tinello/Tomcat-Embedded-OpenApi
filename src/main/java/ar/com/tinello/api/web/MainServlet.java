package ar.com.tinello.api.web;

import java.io.IOException;
import java.util.Map;

import org.openapi4j.core.validation.ValidationException;
import org.openapi4j.operation.validator.validation.OperationValidator;
import org.openapi4j.operation.validator.validation.RequestValidator;

import ar.com.tinello.api.core.Provider;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final var tracer = (Tracer)req.getAttribute(RequestAttributes.TRACER.name());
        final var spanContextParent = (SpanContext)req.getAttribute(RequestAttributes.SPAN_CONTEXT_PARENT.name());
        final var span = tracer.spanBuilder("ProcessRequest")
            .setParent(Context.current().with(Span.wrap(spanContextParent)))
            .startSpan();        

        final var pw = resp.getWriter();
        resp.setContentType("application/json");

        final var requestValidator = (RequestValidator) req.getServletContext().getAttribute(ContextAttributes.REQUEST_VALIDATE.toString());
        final var provider = (Provider) req.getServletContext().getAttribute(ContextAttributes.PROVIDER.toString());
        final var operations = (Map<String, Operation>) req.getServletContext().getAttribute(ContextAttributes.OPERATIONS.toString());

        final var request = ServletRequest.of(req);

        OperationValidator operationValidator;

        try {
            requestValidator.validate(request);
            operationValidator = requestValidator.getValidator(request);
        } catch (ValidationException e) {
            pw.println("{\"message\":\"" + e.getMessage().replaceAll("\"", "").replaceAll("\r", "").replaceAll("\n", "") + "\"}");
            pw.flush();
            pw.close();

            span.setAttribute("OperationValidationException", e.getMessage());
            span.end();
            return;
        }

        final var operation = operationValidator.getOperation();
        final var op = operations.get(operation.getOperationId());
        span.setAttribute("Operation", operation.getOperationId());

        String body;
        try {
            body = op.execute(req, provider, tracer, span.getSpanContext());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            body = "{\"message\":\"" + e.getMessage().replaceAll("\"", "").replaceAll("\r", "").replaceAll("\n", "") + "\"}";
        }

        pw.println(body);
        pw.flush();
        pw.close();
        span.end();
    }
}
