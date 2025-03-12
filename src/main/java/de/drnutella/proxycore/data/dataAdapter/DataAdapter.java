package de.drnutella.proxycore.data.dataAdapter;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.data.MySQL;

import java.util.concurrent.ExecutorService;

class DataAdapter {
    static final ExecutorService executorService = ProxyCore.getExternalExecutorService();
    static final MySQL mysql = ProxyCore.getMySQL();
}
