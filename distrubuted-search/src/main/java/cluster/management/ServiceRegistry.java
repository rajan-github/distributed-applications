package cluster.management;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceRegistry implements Watcher {
    private static final String REGISTRY_ZNODE = "/service_registry";
    private final ZooKeeper zooKeeper;
    private String currentZnode = null;
    private List<String> allServiceAddresses = null;

    public ServiceRegistry(ZooKeeper _zookeeper) {
        this.zooKeeper = _zookeeper;
        createServiceRegisteryZnode();
    }

    public void registerForUpdates() {
        try {
            updateAddresses();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unregisterFromCluster() throws KeeperException, InterruptedException {
        if (currentZnode != null && zooKeeper.exists(currentZnode, false) != null)
            zooKeeper.delete(currentZnode, -1);
    }

    public List<String> getAllServiceAddresses() {
        if (allServiceAddresses == null) {
            try {
                updateAddresses();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return allServiceAddresses;
    }

    public void registerToCluster(String metadata) throws KeeperException, InterruptedException {
        currentZnode = zooKeeper.create(REGISTRY_ZNODE + "/n_", metadata.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(currentZnode + " registered to the service registry.");
    }

    private void createServiceRegisteryZnode() {
        try {
            if (zooKeeper.exists(REGISTRY_ZNODE, false) == null) {
                zooKeeper.create(REGISTRY_ZNODE, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void updateAddresses() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(REGISTRY_ZNODE, this);
        List<String> addresses = new ArrayList<>(children.size());
        for (String child : children) {
            String fullPath = REGISTRY_ZNODE + "/n_" + child;
            Stat stat = zooKeeper.exists(fullPath, false);
            if (stat != null) {
                byte[] nodeAddress = zooKeeper.getData(fullPath, false, stat);
                addresses.add(new String(nodeAddress));
            }
        }
        allServiceAddresses = Collections.unmodifiableList(addresses);
        System.out.println("Cluster addresses: " + allServiceAddresses);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            updateAddresses();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
