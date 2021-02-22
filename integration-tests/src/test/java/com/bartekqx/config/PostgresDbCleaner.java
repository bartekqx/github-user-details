package com.bartekqx.config;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PostgresDbCleaner implements BeforeEachCallback {

    private static final Set<String> TABLES_TO_IGNORE = Set.of(
            "databasechangelog",
            "databasechangeloglock"
    );

    private static final String[] tableType = new String[]{("TABLE")};
    private static final DataSource dataSource = loadDataSource();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        cleanDatabase(dataSource);
    }

    private static DataSource loadDataSource() {
        return DataSourceBuilder
                .create()
                .username(TestContainers.postgreSQLContainer.getUsername())
                .password(TestContainers.postgreSQLContainer.getPassword())
                .url(TestContainers.postgreSQLContainer.getJdbcUrl())
                .driverClassName(TestContainers.postgreSQLContainer.getDriverClassName())
                .build();
    }

    private void cleanDatabase(DataSource dataSource) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            final List<String> tablesToClean = loadTablesToClean(connection);
            cleanTablesData(tablesToClean, connection);
            connection.commit();
        }
    }

    private List<String> loadTablesToClean(Connection connection) throws SQLException {
        final DatabaseMetaData databaseMetaData = connection.getMetaData();

        final ResultSet resultSet = databaseMetaData.getTables(
                connection.getCatalog(), null, null, tableType);

        final List<String> tables = new ArrayList<>();
        while (resultSet.next()) {

            String name = resultSet.getString("TABLE_NAME");
            if (!TABLES_TO_IGNORE.contains(name)) {
                tables.add(name);
            }
        }

        return tables;
    }

    private void cleanTablesData(List<String> tables , Connection connection)
            throws SQLException {
        if (tables.isEmpty()) {
            return;
        }

        final StringBuilder query = new StringBuilder("TRUNCATE ");

        for (int i = 0; i <tables.size(); i++) {
            if (i != 0) {
                query.append(", ");
            }
            query.append(tables.get(i));
        }
        connection.prepareStatement(query.toString()).execute();
    }
}
