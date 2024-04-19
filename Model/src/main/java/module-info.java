module ModelProject {
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    exports org.gameoflife.model;
    exports org.gameoflife.model.exceptions;
}