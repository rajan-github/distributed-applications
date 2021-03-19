package cluster.management;

import org.apache.zookeeper.KeeperException;

public interface OnElectionCallback {
    public void onElectedToBeLeader() throws KeeperException, InterruptedException;

    public void onWorker();
}
