package RMI.zk;

/**
 * Created by Csh on 2019/1/14.
 */
public interface RegisterCenter {

    /**
     * 注册服务和服务地址
     * @param serverName
     * @param serverAdd
     */
    void register(String serverName, String serverAdd);



}
