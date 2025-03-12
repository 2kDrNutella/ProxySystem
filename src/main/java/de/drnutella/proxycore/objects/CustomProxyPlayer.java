package de.drnutella.proxycore.objects;

import de.drnutella.proxycore.data.implementation.UserBasicInformationService;
import de.drnutella.proxycore.utils.TimeCalculator;

import java.sql.Time;
import java.util.UUID;
import java.util.function.Consumer;

public class CustomProxyPlayer {
    UUID uuid;
    UserInformation userInformation;

    public CustomProxyPlayer(String username, Consumer<CustomProxyPlayer> callback) {
        UserBasicInformationService.getUUIDFromUserName(username, uuidFeedback -> {
            if (uuidFeedback == null) {
                callback.accept(null);
            }else {
                this.uuid = uuidFeedback;
            }

            UserBasicInformationService.loadPlayerDatabaseInformation(this.uuid, userInformationFeedback -> {
                this.userInformation = userInformationFeedback;
                callback.accept(this);
            });
        });
    }

    public CustomProxyPlayer(UUID uuid, Consumer<CustomProxyPlayer> callback) {
        this.uuid = uuid;

        UserBasicInformationService.loadPlayerDatabaseInformation(this.uuid, userInformationFeedback -> {
            this.userInformation = userInformationFeedback;
            callback.accept(this);
        });
    }

    public UUID uuid() {
        return this.uuid;
    }

    public Long onlineTime(){
        return ((System.currentTimeMillis() - userInformation.getLoginMillis()) / 1000);
    }

    public String onlineTimeString(){
        return TimeCalculator.convertSecondsToReadableTime(onlineTime());
    }

    public Long loginMillis(){
        return userInformation.getLoginMillis();
    }

    public String loginTimestamp(){
        return userInformation.getLoginTimestamp();
    }

    public Long playTime(){
        return userInformation.getPlayTime() + ((System.currentTimeMillis() - userInformation.getLoginMillis()) / 1000);
    }

    public String playTimeAsString(){
        return TimeCalculator.convertSecondsToReadableTime(playTime());
    }
}
