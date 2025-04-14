package guru.qa.niffler.data;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Databases {

    private Databases() {
    }

    private static final Map<String, DataSource> datasources = new ConcurrentHashMap<>();

    private static DataSource dataSource(String jdbcUrl) {
        return datasources.computeIfAbsent(
                jdbcUrl,
                key -> {
                    PGSimpleDataSource source = new PGSimpleDataSource();
                    source.setUser("postgres");
                    source.setPassword("secret");
                    source.setURL(key);
                    return source;
                }
        );
    }

    public static Connection connection(String jdbcUrl) throws SQLException {
        return dataSource(jdbcUrl).getConnection();
    }

}
