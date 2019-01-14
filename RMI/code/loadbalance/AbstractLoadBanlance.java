package RMI.loadbalance;

import java.util.List;

/**
 * Created by Csh on 2019/1/14.
 */
public abstract  class AbstractLoadBanlance implements  LoadBanalce {
    @Override
    public String selectHost(List<String> repos) {
        if (repos==null || repos.size()==0){
            return null;
        }
        if (repos.size()==1){
            return repos.get(0);
        }
        return doSelect(repos);
    }
    protected  abstract String doSelect(List<String> repos);
}
