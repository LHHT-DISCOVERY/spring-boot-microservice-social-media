package com.example.identity.common.event_tracking;

public class AuditEventSQLBuilder {

    public AuditEventSQLBuilder() {}

    static String builtInsertSQL(AuditEventDBNameResolver auditEventDBNameResolver) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        sqlBuilder.append(auditEventDBNameResolver.getTableName(AuditEventDatabaseName.AUDIT_EVENT));
        sqlBuilder.append(" (");
        sqlBuilder.append(auditEventDBNameResolver.getColumnName(AuditEventColumn.MESSAGE));
        sqlBuilder.append(") ");
        sqlBuilder.append("VALUES (?)");
        return sqlBuilder.toString();
    }
}
