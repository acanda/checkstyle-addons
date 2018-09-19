package ch.acanda.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import java.util.ArrayList;
import java.util.List;

public class ErrorCollector implements AuditListener {

    private final List<String> errors = new ArrayList<>();

    public String getErrors() {
        return String.join("\n", errors);
    }

    @Override
    public void auditStarted(final AuditEvent event) {
        // nothing to do
    }

    @Override
    public void auditFinished(final AuditEvent event) {
        // nothing to do

    }

    @Override
    public void fileStarted(final AuditEvent event) {
        // nothing to do

    }

    @Override
    public void fileFinished(final AuditEvent event) {
        // nothing to do

    }

    @Override
    public void addError(final AuditEvent event) {
        errors.add(String.format("[%S] %s:%s %s",
                                 event.getSeverityLevel(),
                                 event.getLine(),
                                 event.getColumn(),
                                 event.getMessage()));
    }

    @Override
    public void addException(final AuditEvent event, final Throwable throwable) {
        // nothing to do
    }
}
