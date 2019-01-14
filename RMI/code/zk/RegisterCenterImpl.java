package RMI.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Csh on 2019/1/14.
 */
public class RegisterCenterImpl implements  RegisterCenter {

    private CuratorFramework curatorFramework;


    {
        curatorFramework=CuratorFrameworkFactory.builder().
                connectString(ZkConf.ZK_ADDR).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,
                        10)).build();
        curatorFramework.start();
    }

    @Override
    public void register(String serverName, String serverAdd) {

        //注册响应的服务
        String serverPath =  ZkConf.ZK_REG_PATH+"/"+serverName;

        //判断模块服务是否存在, 不存在则进行创建;
        try {
            if (curatorFramework.checkExists().forPath(serverPath)==null){
                    curatorFramework.create().creatingParentsIfNeeded().
                            withMode(CreateMode.PERSISTENT).forPath(serverPath,"0".getBytes());
            }
            String addressPath = serverPath+"/"+serverAdd;
            String reNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL).
                    forPath(addressPath,"0".getBytes());
            System.out.println("服务注册成功: "+reNode);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
