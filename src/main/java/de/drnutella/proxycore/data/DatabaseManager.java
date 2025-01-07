package de.drnutella.proxycore.data;

import de.drnutella.proxycore.data.databaseAdapter.ProxyCoreDatabaseAdapter;
import de.drnutella.proxycore.data.databaseAdapter.data.UserInformationDataAdapter;

public class DatabaseManager {

    public ProxyCoreDatabaseAdapter proxyCoreDatabaseAdapter = new ProxyCoreDatabaseAdapter();
    public UserInformationDataAdapter userInformationDataAdapter = new UserInformationDataAdapter();
}
