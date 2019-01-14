package RMI.client;

import RMI.rmi.RpcRequest;
import RMI.zk.client.ServiceDiscovery;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Csh on 2019/1/10.
 */
public class TCPTransport {
    private String serviceAddress;


    public TCPTransport(String serviceAddress) {
        this.serviceAddress=serviceAddress;
    }

    /**
     * 创建一个socket连接
     */
    private Socket newSocket() {
        System.out.println("创建一个新的连接!");
        Socket socket = null;

        try {
            String[] add = serviceAddress.split(":");

            socket = new Socket(add[0],Integer.parseInt(add[1]));
            return socket;
        } catch (IOException e) {
            throw new RuntimeException(
                    "socket创建失败!"
            );
        }
    }

    /**
     * 发送信息
     */
    public Object send(RpcRequest rpcRequest) {

        Socket socket = null;
        socket = newSocket();
        try {
            //获得输出流, 将客户端需要调用的远程方法发送给服务端;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            //获得输入流 //得到服务端的返回结果;
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用异常!");
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
