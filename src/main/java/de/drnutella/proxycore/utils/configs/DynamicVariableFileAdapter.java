package de.drnutella.proxycore.utils.configs;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.ConfigBuilder;

public class DynamicVariableFileAdapter {
    final static ConfigBuilder builder = ProxyCore.getDynamicVariablesConfigBuilder();

    public static Boolean isWartungActive = (Boolean) builder.getConfig().get("isWartung");

    public static void setWartung(Boolean status){
        isWartungActive = status;
        builder.getConfig().put("isWartung", status);
        builder.save();
    }

}
