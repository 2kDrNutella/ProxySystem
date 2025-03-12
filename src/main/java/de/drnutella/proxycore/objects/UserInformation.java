package de.drnutella.proxycore.objects;

import java.util.UUID;

public class UserInformation {

    UUID uuid;
    long playTime;
    long loginMillis;
    String loginTimestamp;

    public UserInformation(UUID uuid, long playTime, long loginMillis, String loginTimestamp) {
        this.uuid = uuid;
        this.playTime = playTime;
        this.loginTimestamp = loginTimestamp;
        this.loginMillis = loginMillis;
    }

    public long getLoginMillis() {
        return loginMillis;
    }

    public String getLoginTimestamp() {
        return loginTimestamp;
    }
    public long getPlayTime() {
        return playTime;
    }

    public UUID getUuid() {
        return uuid;
    }
}
