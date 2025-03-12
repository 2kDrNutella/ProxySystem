package de.drnutella.proxycore.data;

import de.drnutella.proxycore.objects.UserInformation;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CacheManager {
    public static HashMap<UUID, UserInformation> storedUserInformation = new HashMap<>();
    public static HashMap<String, UUID> storedUUID = new HashMap<>();


    private static List<HashMap> hastList = List.of(
            storedUserInformation
    );

    public static void clearPlayerFromAllCaches(UUID uuid){
        for(HashMap hast : hastList){
            hast.remove(uuid);
        }
    }
}
