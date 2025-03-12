package de.drnutella.proxycore.data.dataAdapter;

import de.drnutella.proxycore.objects.UserInformation;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

public class UserBasicInformationDataAdapter extends DataAdapter {

    static ResultSet resultSet;

    /*
        - Erstellt einen Datensatz für einen neuen Spieler
        - Überprüft ob einer vorhandener Spieler seinen Namen verändert hat
        - Setzt den LastLogin Timestamp in den Spieler Datensatz
    */
    protected static void refreshPlayerOrCreateItSQL(final ProxiedPlayer proxiedPlayer, final Consumer<Boolean> callback) {
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
    protected static void refreshPlaytimeSQL(final ProxiedPlayer proxiedPlayer, final Consumer<Boolean> callback) {
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
        - Lädt alle informationen des Spielers anhand der UUID und erstellt ein UserInfo Objekt
    */
    protected static void getUserInfoFromDatabaseByUUIDSQL(final UUID uuid, final Consumer<UserInformation> callback) {
        executorService.submit(() -> {
            UserInformation result;
            try (final Connection connection = mysql.getConnection()) {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user.uuid = ?");

                preparedStatement.setString(1, uuid.toString());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    long playtime = resultSet.getLong("playtime");
                    String lastLogin = resultSet.getString("lastlogin");

                    result = new UserInformation(uuid, playtime, System.currentTimeMillis(), lastLogin);
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
    protected static void getUUIDByUsernameSQL(final String playerName, final Consumer<UUID> callback) {
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
