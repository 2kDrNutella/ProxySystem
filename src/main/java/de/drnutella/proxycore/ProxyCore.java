package de.drnutella.proxycore;

import de.drnutella.proxycore.commands.*;
import de.drnutella.proxycore.commands.info.*;
import de.drnutella.proxycore.data.DatabaseManager;
import de.drnutella.proxycore.data.MySQL;
import de.drnutella.proxycore.handler.PermissionHandler;
import de.drnutella.proxycore.listener.LogoutListener;
import de.drnutella.proxycore.listener.PingListener;
import de.drnutella.proxycore.listener.PostLoginListener;
import de.drnutella.proxycore.utils.ConfigBuilder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyCore extends Plugin {

    static ProxyCore instance;
    static MySQL mySQL;

    static ExecutorService executorService;

    static ConfigBuilder dynamicVariablesConfigBuilder, configBuilder, permissionsBuilder, mySQLBuilder;
    static PermissionHandler permissionHandler;

    final PluginManager pluginManager = getProxy().getPluginManager();

    @Override
    public void onEnable() {
        instance = this;
        executorService = Executors.newCachedThreadPool();
        permissionHandler = new PermissionHandler();

        loadConfigs();
        registerListener();
        loadCommands();
        loadDatabase();

        getLogger().info("§6ProxyCore §7(§e" + getDescription().getVersion() + "§7) §asuccessfully §a§llaunched!");
    }

    @Override
    public void onDisable() {
        executorService.shutdown();
        mySQL.closePool();
        getLogger().info("§6ProxyCore §7(§e" + getDescription().getVersion() + "§7) §csuccessfully §c§ldisabled!");
    }

    void loadDatabase(){
        DatabaseManager.proxyCoreDatabaseAdapter.createDefaultTables();
    }

    void loadConfigs(){
        dynamicVariablesConfigBuilder = new ConfigBuilder("plugins/ProxyCore", "dynamicVariables.yml", "", "dynamicVariables");
        configBuilder = new ConfigBuilder("plugins/ProxyCore", "config.yml", "", "config");
        permissionsBuilder = new ConfigBuilder("plugins/ProxyCore", "permissions.yml", "", "permissions");
        mySQLBuilder = new ConfigBuilder("plugins/ProxyCore", "mysql.yml", "", "mysql");

        mySQL = new MySQL();
    }

    void loadCommands(){
        pluginManager.registerCommand(this, new WartungCommand());
        pluginManager.registerCommand(this, new TeamChatCommand());
        pluginManager.registerCommand(this, new FindCommand());
        pluginManager.registerCommand(this, new JumpToCommand());
        pluginManager.registerCommand(this, new SendAllCommand());
        pluginManager.registerCommand(this, new HubCommand());

        pluginManager.registerCommand(this, new DiscordCommand());
        pluginManager.registerCommand(this, new ShopCommand());
        pluginManager.registerCommand(this, new TeamCommand());
        pluginManager.registerCommand(this, new PlayTimeCommand());
        pluginManager.registerCommand(this, new PlayerInfoCommand());
    }

    void registerListener() {
        pluginManager.registerListener(this, new PingListener());
        pluginManager.registerListener(this, new PostLoginListener());
        pluginManager.registerListener(this, new LogoutListener());
    }

    public static ExecutorService getExternalExecutorService() {
        return executorService;
    }

    public static ConfigBuilder getDynamicVariablesConfigBuilder() {
        return dynamicVariablesConfigBuilder;
    }

    public static ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public static ConfigBuilder getMySQLBuilder() {
        return mySQLBuilder;
    }

    public static ConfigBuilder getPermissionsBuilder() {
        return permissionsBuilder;
    }

    public static ProxyCore getInstance() {
        return instance;
    }

    public static MySQL getMySQL() {
        return mySQL;
    }

    public static PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
