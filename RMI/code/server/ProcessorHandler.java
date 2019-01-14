package RMI.server;

import RMI.rmi.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Csh on 2019/1/10.
 */
public class ProcessorHandler implements Runnable
{

    private Socket socket;

    Map<String,Object> handlerMap;


    public ProcessorHandler(Socket socket, Map<String,Object> handlerMap) {
        this.handlerMap=handlerMap;
        this.socket = socket;
    }

    @Override
    public void run() {
        //处理请求
        ObjectInputStream objectInputStream = null;

        try {
            //获得输入流
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            //反序列化远程传输对象rpcrequest
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object object =invoke(rpcRequest);//通过反射调用本地方法


            //通过输出流讲结果输出给客户端
            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(object);
            outputStream.flush();
            objectInputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Object invoke(RpcRequest rpcRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //一下均为反射操作，目的是通过反射调用服务
        Object[] args=rpcRequest.getParams();
        Class<?>[] types=new Class[args.length];
        for(int i=0;i<args.length;i++){
            types[i]=args[i].getClass();
        }
        String serverName = rpcRequest.getClassName();
        String version = rpcRequest.getVersion();
        if (version!=null && !version.equals("")){
            serverName=serverName+"-"+version;
        }
        //从handlerMap中 根据客户端请求的地址, 去拿响应的服务, 通过反射发起调用
        Object service = handlerMap.get(serverName);
        Method method=service.getClass().getMethod(rpcRequest.getMethodName(),types);
        return method.invoke(service,args);


    }
}
