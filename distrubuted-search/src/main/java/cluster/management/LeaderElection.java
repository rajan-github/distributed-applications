package cluster.management;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;

public class LeaderElection implements Watcher {
    private static final String ELECTION_NAMESPACE = "/election";
    private ZooKeeper zooKeeper;
    private String currentZnodeName;

    public LeaderElection(ZooKeeper _zookeeper) {
        this.zooKeeper = _zookeeper;
    }

    public void volunteeForLeaderShip() throws KeeperException, InterruptedException {
        String znodePrefix = ELECTION_NAMESPACE + "/c_";
        currentZnodeName = zooKeeper.create(znodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("znodeFullPath: " + currentZnodeName);
    }

    public void reelectLeader() throws KeeperException, InterruptedException {
        Stat predecessorStat = null;
        String predecessorName = null;
        while (predecessorStat == null) {
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);
            String leader = children.get(0);
            if (leader.equals(currentZnodeName)) {
                System.out.println(String.format("I %s am the leader", currentZnodeName));
            } else {
                int predecessorIndex = Collections.binarySearch(children, currentZnodeName) - 1;
                predecessorName = children.get(predecessorIndex);
                zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorName, this);
            }
        }

        System.out.println("Watching znode: " + predecessorName);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case NodeDeleted:
                System.out.println("Node is deleted! Reelecting the leader.");
                try {
                    reelectLeader();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
