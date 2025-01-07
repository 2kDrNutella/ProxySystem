package de.drnutella.proxycore.utils;

import net.luckperms.api.model.group.Group;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String getColorCodeFromGroup(final Group group){
        final String prefix = group.getCachedData().getMetaData().getPrefix().replace("&", "§");
        final String colorFromPrefix = getColorCodeFromPrefix(prefix);
        return (colorFromPrefix != null ? colorFromPrefix : "§f");
    }

    public static String getColorCodeFromPrefix(final String prefix) {
        if (prefix == null) return null;

        final Pattern pattern = Pattern.compile("§[0-9a-f]");
        final Matcher matcher = pattern.matcher(prefix);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
