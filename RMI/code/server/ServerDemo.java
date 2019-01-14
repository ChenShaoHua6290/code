package RMI.server;

import RMI.rmi.HelloService;
import RMI.zk.RegisterCenter;
import RMI.zk.RegisterCenterImpl;

import java.io.IOException;

/**
 * Created by Csh on 2019/1/10.
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        HelloService helloService = new HelloServiceImpl();
        RegisterCenter registerCenter = new RegisterCenterImpl();
        Server server = new Server(registerCenter,"127.0.0.1:8080");
        server.bind(helloService);
        server.pubServer();
        System.in.read();
    }
}
