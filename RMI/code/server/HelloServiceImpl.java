package RMI.server;

import RMI.Anno.RpcRegAnno;
import RMI.rmi.HelloService;

/**
 * Created by Csh on 2019/1/10.
 */
@RpcRegAnno(value = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "Hi!"+msg;
    }

}
