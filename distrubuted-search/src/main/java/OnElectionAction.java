import cluster.management.OnElectionCallback;
import cluster.management.ServiceRegistry;
import org.apache.zookeeper.KeeperException;
import search.SearchWorker;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OnElectionAction implements OnElectionCallback {
    private final ServiceRegistry serviceRegistry;
    private final int port;
    private Webserver webserver;

    public OnElectionAction(ServiceRegistry _serviceRegistry, int port) {
        this.serviceRegistry = _serviceRegistry;
        this.port = port;
    }


    @Override
    public void onElectedToBeLeader() throws KeeperException, InterruptedException {
        serviceRegistry.unregisterFromCluster();
        serviceRegistry.registerForUpdates();
    }

    @Override
    public void onWorker() {
        SearchWorker searchWorker = new SearchWorker();
        webserver = new WebServer(port, searchWorker);
        webserver.startServer();

        try {
            String currentServerAddress = String.format("http://%s:%d%s", InetAddress.getLocalHost().getCanonicalHostName(), port, searchWorker.getEndPoint());
            serviceRegistry.registerToCluster(currentServerAddress);
        } catch (UnknownHostException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
