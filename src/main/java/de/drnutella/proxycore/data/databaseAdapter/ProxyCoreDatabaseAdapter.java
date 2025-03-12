package de.drnutella.proxycore.data.databaseAdapter;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.data.MySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
    Datenbank-Schnittstelle für alle MySQL Statements, die auf die primäre Table erstellung abziehlen
*/
public class ProxyCoreDatabaseAdapter extends DatabaseAdapter{

    public ProxyCoreDatabaseAdapter() {
        super(ProxyCore.getMySQL());
    }

    private final Boolean areTablesCreated = (Boolean) ProxyCore.getMySQLBuilder().getConfig().get("tables-Created");

    public void createDefaultTables() {
        if (!areTablesCreated) {
            createUserTable();
            createUserCurrencyTable();
            ProxyCore.getMySQLBuilder().getConfig().put("tables-Created", true);
            ProxyCore.getMySQLBuilder().save();
        }
    }

    private void createUserTable() {
        try {
            final PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("" +
                    "CREATE TABLE `user` (" +
                    "  `uuid` VARCHAR(36) NOT NULL," +
                    "  `username` VARCHAR(16) NOT NULL," +
                    "  `playtime` BIGINT NULL," +
                    "  `lastlogin` TIMESTAMP NULL," +
                    "  PRIMARY KEY (`uuid`)); "
                    );

            preparedStatement.executeUpdate();
            ProxyCore.getInstance().getLogger().info("Table {user} erfolgreich erstellt!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            ProxyCore.getInstance().getLogger().warning("FEHLER BEI Table {user} ERSTELLUNG!");

        }
    }

    private void createUserCurrencyTable() {
        try {
            final PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("CREATE TABLE `usercurrency` (" +
                    "  `uuid` VARCHAR(36) NOT NULL," +
                    "  `type` VARCHAR(30) NOT NULL," +
                    "  `amount` BIGINT DEFAULT NULL," +
                    "  PRIMARY KEY (`uuid`,`type`));"
                    );

            preparedStatement.executeUpdate();
            ProxyCore.getInstance().getLogger().info("Table {usercurrency} erfolgreich erstellt!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            ProxyCore.getInstance().getLogger().warning("FEHLER BEI Table {usercurrency} ERSTELLUNG!");

        }
    }

}
