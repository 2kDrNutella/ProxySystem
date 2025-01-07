package de.drnutella.proxycore.objects;

import java.util.UUID;

public class UserInfoObject{

    UUID uuid;
    String username;
    int coins;
    int juweelen;
    int playtimeInSeconds;

    String lastLogin;

    public UserInfoObject(UUID uuid, String username, int coins, int juweelen, int playtimeInSeconds, String lastLogin) {
        this.uuid = uuid;
        this.username = username;
        this.coins = coins;
        this.juweelen = juweelen;
        this.playtimeInSeconds = playtimeInSeconds;
        this.lastLogin = lastLogin;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }

    public int getJuweelen() {
        return juweelen;
    }

    public int getPlaytimeInSeconds() {
        return playtimeInSeconds;
    }

    public String getLastLogin() {
        return lastLogin;
    }
}
