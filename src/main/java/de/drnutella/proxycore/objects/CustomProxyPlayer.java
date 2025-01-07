package de.drnutella.proxycore.objects;

import de.drnutella.proxycore.ProxyCore;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CustomProxyPlayer {
    ProxiedPlayer player;
    int coins;
    int juweelen;
    long playtimeInSeconds;
    long loginTimeInMillis;

    public CustomProxyPlayer(ProxiedPlayer player, int coins, int juweelen, long playtimeInSeconds, long loginTimeInMillis) {
        this.coins = coins;
        this.juweelen = juweelen;
        this.loginTimeInMillis = loginTimeInMillis;
        this.player = player;
        this.playtimeInSeconds = playtimeInSeconds;

        saveCustomPlayerInList();
    }

    private void saveCustomPlayerInList(){
        ProxyCore.getCacheManager().userInformationCache.put(player.getUniqueId(), this);
    }

    public int getCurrentPlaytime(){
        return (int) ((System.currentTimeMillis() - getLoginTimeInMillis()) / 1000);
    }

    public long getRefreshPlaytime(){
        int currentOnlineTime = (int) ((System.currentTimeMillis() - getLoginTimeInMillis()) / 1000);
        long savedSeconds = getPlaytimeInSeconds();
        return savedSeconds + currentOnlineTime;
    }

    public int getCoins() {
        return coins;
    }

    public CustomProxyPlayer setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public int getJuweelen() {
        return juweelen;
    }

    public CustomProxyPlayer setJuweelen(int juweelen) {
        this.juweelen = juweelen;
        return this;
    }

    public long getLoginTimeInMillis() {
        return loginTimeInMillis;
    }

    public CustomProxyPlayer setLoginTimeInMillis(long loginTimeInMillis) {
        this.loginTimeInMillis = loginTimeInMillis;
        return this;
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public CustomProxyPlayer setPlayer(ProxiedPlayer player) {
        this.player = player;
        return this;
    }

    public long getPlaytimeInSeconds() {
        return playtimeInSeconds;
    }

    public CustomProxyPlayer setPlaytimeInSeconds(long playtimeInSeconds) {
        this.playtimeInSeconds = playtimeInSeconds;
        return this;
    }
}
