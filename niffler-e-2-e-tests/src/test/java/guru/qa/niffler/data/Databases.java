package guru.qa.niffler.data;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import jakarta.transaction.UserTransaction;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class Databases {

    private Databases() {
    }

    private static final Map<String, DataSource> datasources = new ConcurrentHashMap<>();
    private static final Map<Long, Map<String, Connection>> threadConnections = new ConcurrentHashMap<>();

    public record XaFunction<T>(Function<Connection, T> function, String jdbcUrl) {};
    public record XaConsumer(Consumer<Connection> function, String jdbcUrl) {};

    public enum TransactionIsolationLvl {
        NONE(Connection.TRANSACTION_NONE),
        READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
        READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
        REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
        SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);
        private final int level;

        TransactionIsolationLvl(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    public static DataSource dataSource(String jdbcUrl) {
        return datasources.computeIfAbsent(
                jdbcUrl,
                key -> {
                    AtomikosDataSourceBean dsBean = new AtomikosDataSourceBean();
                    final String uniqId = StringUtils.substringAfter(jdbcUrl, "5432/");
                    dsBean.setUniqueResourceName(uniqId);
                    dsBean.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
                    Properties props = new Properties();
                    props.put("URL", jdbcUrl);
                    props.put("user", "postgres");
                    props.put("password", "secret");
                    dsBean.setXaProperties(props);
                    dsBean.setPoolSize(3);
                    dsBean.setMaxPoolSize(10);
                    return dsBean;
                }
        );
    }

    public static <T> T transaction(Function<Connection, T> function, String jdbcUrl, TransactionIsolationLvl lvlIsolation) {
        Connection connection = null;
        try {
            connection = connection(jdbcUrl, lvlIsolation);
            connection.setAutoCommit(false);

            T result = function.apply(connection);

            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public static <T> T xaTransaction(TransactionIsolationLvl lvlIsolation, XaFunction<T>... actions) {
        UserTransaction ut = new UserTransactionImp();
        try {
            ut.begin();
            T result = null;
            for (XaFunction<T> action : actions) {
                result = action.function().apply(connection(action.jdbcUrl, lvlIsolation));
            }
            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public static void transaction(Consumer<Connection> consumer, String jdbcUrl, TransactionIsolationLvl lvlIsolation) {
        Connection connection = null;
        try {
            connection = connection(jdbcUrl, lvlIsolation);
            connection.setAutoCommit(false);

            consumer.accept(connection);

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public static void xaTransaction(TransactionIsolationLvl lvlIsolation, XaConsumer... actions) {
        UserTransaction ut = new UserTransactionImp();
        try {
            ut.begin();
            for (XaConsumer action : actions) {
                action.function().accept(connection(action.jdbcUrl, lvlIsolation));
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public static Connection connection(String jdbcUrl, TransactionIsolationLvl lvlIsolation) throws SQLException {
        return threadConnections.computeIfAbsent(
                Thread.currentThread().threadId(),
                key -> {
                    try {
                        Connection connection = dataSource(jdbcUrl).getConnection();
                        connection.setTransactionIsolation(lvlIsolation.getLevel());
                        return new HashMap<>(Map.of(jdbcUrl, connection));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).computeIfAbsent(
                jdbcUrl,
                key -> {
                    try {
                        Connection connection = dataSource(jdbcUrl).getConnection();
                        connection.setTransactionIsolation(lvlIsolation.getLevel());
                        return connection;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public static void closeAllConnections() {
        for (Map<String, Connection> connectionMap : threadConnections.values()) {
            for (Connection connection : connectionMap.values()) {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    //NOP
                }
            }
        }
    }
}
