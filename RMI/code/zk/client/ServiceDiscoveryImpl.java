package RMI.zk.client;

import RMI.loadbalance.LoadBanalce;
import RMI.loadbalance.RandomLoadBanlance;
import RMI.zk.ZkConf;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Csh on 2019/1/14.
 */
public class ServiceDiscoveryImpl implements  ServiceDiscovery {
    List<String> repos=new ArrayList<>();


    private  String address;

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl(String address){
        this.address = address;
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(address).sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();

    }

    @Override
    public String discovery(String serverName) {
        String serverPath = ZkConf.ZK_REG_PATH+"/"+serverName;
        try {
             repos = curatorFramework.getChildren().forPath(serverPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        registerWatcher(serverPath);
        //伪负载均衡机制
        LoadBanalce loadBanalce  = new RandomLoadBanlance();
        return loadBanalce.selectHost(repos);
    }



    //注册监听机制

    private  void registerWatcher(final  String path){
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);

            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);;
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            System.out.println("注册pathChild watcher 异常"+e);
            e.printStackTrace();
        }
    }
}
