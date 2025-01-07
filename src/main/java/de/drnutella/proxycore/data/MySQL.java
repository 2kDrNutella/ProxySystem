package de.drnutella.proxycore.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.ConfigBuilder;

import java.sql.*;

public class MySQL {

    static final ConfigBuilder CONFIG_BUILDER = ProxyCore.getMySQLBuilder();
    static final ProxyCore PROXY_CORE_INSTANCE = ProxyCore.getInstance();

    static final String HOST = (String) CONFIG_BUILDER.getConfig().get("host");
    static final String PORT = (String) CONFIG_BUILDER.getConfig().get("port");
    static final String DATABASE = (String) CONFIG_BUILDER.getConfig().get("database");
    static final String USERNAME = (String) CONFIG_BUILDER.getConfig().get("username");
    static final String PASSWORD = (String) CONFIG_BUILDER.getConfig().get("password");

    static final HikariDataSource HIKARI_DATA_SOURCE;

    //   - Erstellt die HikariConfig und den HikariPool

    static {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE);
        hikariConfig.setUsername(USERNAME);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setIdleTimeout(600000); // 10 Minuten, bis eine inaktive Verbindung geschlossen wird
        hikariConfig.setMaxLifetime(1800000); // 30 Minuten maximale Lebensdauer einer Verbindung
        hikariConfig.setMinimumIdle(20);
        hikariConfig.setMaximumPoolSize(250);

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HIKARI_DATA_SOURCE = new HikariDataSource(hikariConfig);
        PROXY_CORE_INSTANCE.getLogger().info("§aMySQL-Connection-Pool wurde eingerichtet!");
    }

    public Connection getConnection() {
        try {
            return HIKARI_DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            PROXY_CORE_INSTANCE.getLogger().warning("§cMySQL-Verbindung konnte nicht abgerufen werden!");
            e.printStackTrace();
            return null;
        }
    }

    public void closePool() {
        if (!HIKARI_DATA_SOURCE.isClosed()) {
            HIKARI_DATA_SOURCE.close();
            PROXY_CORE_INSTANCE.getLogger().info("§cMySQL-Connection-Pool wurde geschlossen!");
        }
    }
}
