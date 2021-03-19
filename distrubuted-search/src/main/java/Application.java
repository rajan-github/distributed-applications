import cluster.management.LeaderElection;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class Application implements Watcher {
    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static final String ELECTION_NAMESPACE = "/election";
    private static String currentZnodeName = "";
    private static ZooKeeper zooKeeper;


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Application application = new Application();
        application.connectToZookeeper();
        LeaderElection leaderElection = new LeaderElection(zooKeeper);
        leaderElection.volunteeForLeaderShip();
        leaderElection.reelectLeader();
        application.run();
        application.close();
        System.out.println("Disconnected from the zookeeper. Exiting.");
    }

    public void connectToZookeeper() throws IOException, InterruptedException, KeeperException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
        System.out.println("Disconnected from the zookeeper.");
    }

    public void run() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to the zookeeper.");
                } else {
                    synchronized (zooKeeper) {
                        System.out.println("Disconnected from zookeeper.");
                        zooKeeper.notifyAll();
                    }
                }
            case NodeDeleted:
                System.out.println("Node is deleted. Calling reelect leader.");
        }
    }
}
