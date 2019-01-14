package RMI.client;

import RMI.rmi.HelloService;
import RMI.zk.ZkConf;
import RMI.zk.client.ServiceDiscovery;
import RMI.zk.client.ServiceDiscoveryImpl;

/**
 * Created by Csh on 2019/1/10.
 */
public class ClientDemo {
    public static void main(String[] args) {

        //得到注册中心地址
        ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(ZkConf.ZK_ADDR);

        RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscovery);
        HelloService helloService = rpcClientProxy.clientProxy(HelloService.class,
                null);
        System.out.println(helloService.sayHello("___呵呵呵"));
    }
}
