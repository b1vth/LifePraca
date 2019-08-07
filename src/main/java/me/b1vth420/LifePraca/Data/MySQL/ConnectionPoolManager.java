package me.b1vth420.LifePraca.Data.MySQL;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final Main plugin;
    private HikariDataSource dataSource;

    public ConnectionPoolManager(Main plugin) {
        this.plugin = plugin;
        setupPool(Config.getInst().mysqlIP, Config.getInst().mysqlPort, Config.getInst().mysqlDatabase, Config.getInst().mysqlUsername, Config.getInst().mysqlPassword);
    }


    private void setupPool(String hostname, int port, String database, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(username);
        config.setPassword(password);

        config.setConnectionTimeout(60000);
        config.setMaxLifetime(60000);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
