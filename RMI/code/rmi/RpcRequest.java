package RMI.rmi;

import java.io.Serializable;

/**
 * Created by Csh on 2019/1/10.
 */
public class RpcRequest implements Serializable{


    private static final long serialVersionUID = -8752964442786338424L;
    private String className;
    private String methodName;
    private Object [] params;
    private String  version;




    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
