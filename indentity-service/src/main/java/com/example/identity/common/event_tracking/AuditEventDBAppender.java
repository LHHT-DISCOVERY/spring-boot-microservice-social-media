package com.example.identity.common.event_tracking;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class AuditEventDBAppender extends DBAppender {
    static final Logger LOGGER = LoggerFactory.getLogger(AuditEventDBAppender.class);
    private String insertSQLStm;
    protected static final Method GET_GENERATED_KEYS_METHOD;
    private AuditEventDBNameResolver auditEventDBNameResolver;

    public void start() {
        super.start();
        if (this.auditEventDBNameResolver == null) {
            this.auditEventDBNameResolver = new AuditEventDBNameResolver();
        }

        this.insertSQLStm = AuditEventSQLBuilder.builtInsertSQL(this.auditEventDBNameResolver);
    }

    protected void subAppend(ILoggingEvent iLoggingEvent, Connection connection, PreparedStatement insertStatement)
            throws Throwable {
        this.bindingLoggingEventWithInsertStatement(insertStatement, iLoggingEvent);
        int updateCount = -1;
        try {
            updateCount = insertStatement.executeUpdate();
        } catch (Exception var6) {
            LOGGER.error("Append log got exception, message:{}", var6.getMessage(), var6);
        }

        if (updateCount != 1) {
            this.addWarn("Failed to insert loggingEvent");
        }
    }

    protected void secondarySubAppend(ILoggingEvent iLoggingEvent, Connection connection, long eventId)
            throws Throwable {}

    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    protected String getInsertSQL() {
        return this.insertSQLStm;
    }

    private void bindingLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent iLoggingEvent)
            throws SQLException {
        stmt.setString(1, iLoggingEvent.getFormattedMessage());
    }

    protected void insertProperties(Map<String, String> mergeMap, Connection connection, long eventId)
            throws SQLException {}

    static {
        Method getGenerateKeysMethod;
        try {
            getGenerateKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);

        } catch (Exception e) {
            getGenerateKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGenerateKeysMethod;
    }
}
