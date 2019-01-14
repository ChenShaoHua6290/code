package RMI.client;

import RMI.zk.client.ServiceDiscovery;

import java.lang.reflect.Proxy;

/**
 * Created by Csh on 2019/1/10.
 */
public class RpcClientProxy {
    /**
     * 创建客户端的远程代理, 通过远程代理进行访问
     * @param interfaceCls
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    private ServiceDiscovery serviceDiscovery;

    public RpcClientProxy(ServiceDiscovery serviceDiscovery){
        this.serviceDiscovery= serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T>
                                     interfaceCls, String version){

        return (T) Proxy.newProxyInstance(interfaceCls
        .getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandler(serviceDiscovery,version));
    }

}
