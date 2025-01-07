package de.drnutella.proxycore.data.databaseAdapter;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.data.MySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
    Datenbank-Schnittstelle für alle MySQL Statements, die auf die primäre Table erstellung abziehlen
*/
public class ProxyCoreDatabaseAdapter {

    private final MySQL mySQL = ProxyCore.getMySQL();
    private final Boolean areTablesCreated = (Boolean) ProxyCore.getMySQLBuilder().getConfig().get("tables-Created");

    /*
        - Überprüft anhand einer Config, ob die Tables bereits erstellt worden sind
        - Spart durch Config Zugriff einen Datenbank aufruf.
     */
    public void createDefaultTables() {
        if (!areTablesCreated) {
            createUserTable();
            ProxyCore.getMySQLBuilder().getConfig().put("tables-Created", true);
            ProxyCore.getMySQLBuilder().save();
        }
    }

    /*
        - Erstellt den User Table
     */
    private void createUserTable() {
        try {
            final PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user` (" +
                    "  `uuid` CHAR(36) NOT NULL," +
                    "  `username` VARCHAR(16) NULL," +
                    "  `coins` INT NULL," +
                    "  `juweelen` INT NULL," +
                    "  `playtime` BIGINT(0) NULL," +
                    "  `lastlogin` TIMESTAMP NULL," +
                    "  PRIMARY KEY (`uuid`));");

            preparedStatement.executeUpdate();
            ProxyCore.getInstance().getLogger().info("Table {user} erfolgreich erstellt!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            ProxyCore.getInstance().getLogger().warning("FEHLER BEI Table {user} ERSTELLUNG!");

        }
    }

}
