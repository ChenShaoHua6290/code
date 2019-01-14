package RMI.client;

import RMI.rmi.RpcRequest;
import RMI.zk.client.ServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Csh on 2019/1/10.
 */
public class RemoteInvocationHandler implements InvocationHandler{

    private String version;
    private ServiceDiscovery serviceDiscovery;

    public RemoteInvocationHandler(ServiceDiscovery serviceDiscovery ,String version) {
        this.version = version;
        this.serviceDiscovery= serviceDiscovery;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        rpcRequest.setVersion(version);

        //根据接口名得到对应的地址
        String serviceAdd= serviceDiscovery.discovery(rpcRequest.getClassName());

    //通过tcp传输协议进行传输
        TCPTransport tcpTransport=new TCPTransport(serviceAdd);
        //发送请求
        return tcpTransport.send(rpcRequest);

    }





}
