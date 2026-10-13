package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/*ConnectionPool holder et lille antal åbne forbindelser til databasen klar,
så vi ikke skal åbne en ny forbindelse hver gang en mapper laver en query.*/
public class ConnectionPool {

    private static HikariDataSource dataSource;

    //Opretter poolen. Kaldes kun én gang i Main.
    public static ConnectionPool getInstance(String user, String password, String url, String db) {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format(url, db)); //%s i url'en bliver skiftet ud med navnet på databasen
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(3);
            dataSource = new HikariDataSource(config);
        }
        return new ConnectionPool();
    }

    //Låner en forbindelse fra poolen. Når den lukkes (try-with-resources) gives den tilbage.
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //Lukker hele poolen
    public void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }
}