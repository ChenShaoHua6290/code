package RMI.server;


import RMI.Anno.RpcRegAnno;
import RMI.zk.RegisterCenter;
import com.sun.javafx.fxml.builder.JavaFXFontBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Csh on 2019/1/10.
 * 用户发布一个远程服务
 */
public class Server {
    //创建一个线程池
    private static final ExecutorService executor = Executors.newCachedThreadPool();


    private RegisterCenter registerCenter;  //注册中心

    private  String serverAddress;//服务地址

    public Server (RegisterCenter registerCenter,String serverAddress){
        this.registerCenter= registerCenter;
        this.serverAddress= serverAddress;
    }


    //存放服务名称和对象之间的关系;
    Map<String ,Object> hashMap = new HashMap<>();

    public  void bind(Object... servers){
        for (Object server: servers){
            RpcRegAnno anno = server.getClass().getAnnotation(RpcRegAnno.class);
            String serverName = anno.value().getName();
            String version = anno.version();
            if (version!=null && !version.equals("")){
                serverName = serverName+"-"+version;
            }
            System.out.println("severName"+serverName+">>>"+server);
            hashMap.put(serverName,server);
        }

    }

    /**
     * 发布服务
     */
    public void pubServer() {
        ServerSocket serverSocket = null;

        try {
            String[] add = serverAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(add[1]));//启动一个监听
            for (String interfaceName: hashMap.keySet()){
                registerCenter.register(interfaceName,serverAddress);
                System.out.println("注册服务成功："+interfaceName+"->"+serverAddress);
            }

            while (true){//循环监听
                Socket socket = serverSocket.accept(); //监听服务
                //通过线程池去处理请求
                executor.execute(new ProcessorHandler(socket,hashMap));
            }

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
