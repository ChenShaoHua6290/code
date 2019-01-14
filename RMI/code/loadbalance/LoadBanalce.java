package RMI.loadbalance;

import java.util.List;

/**
 * Created by Csh on 2019/1/14.
 */
public interface LoadBanalce {

    String selectHost(List<String> repos);
}
