package com.example.identity.common.event_tracking;

import ch.qos.logback.classic.db.names.DBNameResolver;

public class AuditEventDBNameResolver implements DBNameResolver {
    private String tableNamePrefix = "";

    public String getTableName() {
        return tableNamePrefix;
    }

    public void setTableName(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }

    public <N extends Enum<?>> String getColumnName(N columName) {
        return columName.name().toLowerCase();
    }

    public <N extends Enum<?>> String getTableName(N tableName) {
        if (tableName.name().equalsIgnoreCase(AuditEventDatabaseName.AUDIT_EVENT.name())) {
            return tableNamePrefix + AuditEventDatabaseName.AUDIT_EVENT.name();
        } else {
            throw new IllegalArgumentException(tableName + "is an unknown table name");
        }
    }
}
