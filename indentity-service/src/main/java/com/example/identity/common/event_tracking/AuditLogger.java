package com.example.identity.common.event_tracking;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class AuditLogger {
    private static final Logger eventLogger = LoggerFactory.getLogger(AuditLogger.class);

    private static final Marker AUDIT_EVENT = MarkerFactory.getMarker("AUDIT_EVENT");

    private AuditLogger() {}

    public static void info(String userName, AuditEventType auditEventType, AuditStatus auditStatus, String detail) {
        try {
            String message = createLogMessage(userName, auditEventType, auditStatus, detail)
                    .toString();
            eventLogger.info(AUDIT_EVENT, message);
        } catch (Exception var1) {
            eventLogger.error("Log info got exception, message{}", var1.getMessage(), var1);
        }
    }

    public static void error(String userName, AuditEventType auditEventType, AuditStatus auditStatus, String detail) {
        try {
            String message = createLogMessage(userName, auditEventType, auditStatus, detail)
                    .toString();
            eventLogger.error(AUDIT_EVENT, message);
        } catch (Exception var1) {
            eventLogger.error("Log info got exception, message{}", var1.getMessage(), var1);
        }
    }

    private static StringBuilder createLogMessage(
            String username, AuditEventType eventType, AuditStatus auditStatus, String details) {
        StringBuilder stringBuilder = new StringBuilder();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        stringBuilder.append(dateTimeFormatter.format(zonedDateTime));
        stringBuilder.append("|");
        stringBuilder.append(username);
        stringBuilder.append("|");
        stringBuilder.append(auditStatus);
        stringBuilder.append("|");
        stringBuilder.append(details);
        return stringBuilder;
    }
}
