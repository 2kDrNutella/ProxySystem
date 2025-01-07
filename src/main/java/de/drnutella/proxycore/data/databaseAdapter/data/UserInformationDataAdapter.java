package de.drnutella.proxycore.data.databaseAdapter.data;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.data.MySQL;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import de.drnutella.proxycore.objects.CustomProxyPlayer;
import de.drnutella.proxycore.objects.UserInfoObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/*
    Datenbank-Schnittstelle für alle MySQL Statements, die auf den User bezogen sind
*/

public class UserInformationDataAdapter {

    static final ExecutorService executorService = ProxyCore.getExternalExecutorService();
    static ResultSet resultSet;
    static final MySQL mysql = ProxyCore.getMySQL();


    /*
      - Erstellt einen Datensatz für einen neuen Spieler
      - Überprüft ob einer vorhandener Spieler seinen Namen verändert hat
      - Setzt den LastLogin Timestamp in den Spieler Datensatz
    */
    public void refreshPlayerOrCreateIt(final ProxiedPlayer proxiedPlayer, final Consumer<Boolean> callback) {
        executorService.submit(() -> {
            boolean result;
            try (final Connection connection = mysql.getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("call checkUserUpToDate(?, ?)");

                preparedStatement.setString(1, proxiedPlayer.getUniqueId().toString());
                preparedStatement.setString(2, proxiedPlayer.getName());

                preparedStatement.executeUpdate();

                result = true;
                preparedStatement.close();
            } catch (SQLException ignored) {
                result = false;
            }
            callback.accept(result);
        });
    }

    /*
      - Lädt die aktuelle Spielzeit
      - Berechnet die Spielzeit in Sekunden
      - Aktualiesert anschließend die Spielzeit
    */
    public void refreshPlaytime(final ProxiedPlayer proxiedPlayer, final Consumer<Boolean> callback) {
        executorService.submit(() -> {
            boolean result;
            try (final Connection connection = mysql.getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("call calculatePlaytime(?)");

                preparedStatement.setString(1, proxiedPlayer.getUniqueId().toString());
                preparedStatement.executeUpdate();

                result = true;

                preparedStatement.close();

            } catch (SQLException ignored) {
                result = false;
            }
            callback.accept(result);
        });
    }

    /*
      - Lädt alle informationen des Spielers anhand der UUID und erstellt das Cache Objekt
    */
    public void createUserObjectFromDatabase(final ProxiedPlayer player, final Consumer<Boolean> callback) {
        executorService.submit(() -> {
            boolean result;
            try (final Connection connection = mysql.getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user.UUID = ?");

                preparedStatement.setString(1, player.getUniqueId().toString());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    new CustomProxyPlayer(
                            player,
                            resultSet.getInt("coins"),
                            resultSet.getInt("juweelen"),
                            resultSet.getInt("playtime"),
                            System.currentTimeMillis()
                    );

                    resultSet.close();
                    result = true;
                } else {
                    resultSet.close();
                    result = false;
                }
                preparedStatement.close();
            } catch (SQLException ignored) {
                result = false;
            }
            callback.accept(result);
        });
    }

    /*
      - Lädt alle informationen des Spielers anhand der UUID und erstellt ein UserInfo Objekt
    */
    public void getUserInfoFromDatabaseByUUID(final UUID uuid, final String playerName, final Consumer<UserInfoObject> callback) {
        executorService.submit(() -> {
            UserInfoObject result;
            try (final Connection connection = mysql.getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user.uuid = ?");

                preparedStatement.setString(1, uuid.toString());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    int coins = resultSet.getInt("coins");
                    int juweelen = resultSet.getInt("juweelen");
                    int playtime = resultSet.getInt("playtime");
                    String lastLogin = resultSet.getString("lastlogin");

                    result = new UserInfoObject(uuid, playerName, coins, juweelen, playtime, lastLogin);
                    resultSet.close();

                } else {
                    resultSet.close();
                    result = null;
                }

                preparedStatement.close();

            } catch (SQLException ignored) {
                result = null;
            }
            callback.accept(result);
        });
    }

    /*
      - Lädt die UUID anhand des Spielernames aus der Datenbank
    */
    public void getUUIDByUsername(final String playerName, final Consumer<UUID> callback) {
        executorService.submit(() -> {
            UUID result;
            try (final Connection connection = mysql.getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT user.uuid FROM user WHERE user.username = ?");

                preparedStatement.setString(1, playerName);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    result = UUID.fromString(resultSet.getString("uuid"));
                } else {
                    resultSet.close();
                    result = null;
                }
                preparedStatement.close();
            } catch (SQLException ignored) {
                result = null;
            }
            callback.accept(result);
        });
    }

}
