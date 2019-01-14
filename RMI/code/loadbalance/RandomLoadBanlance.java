package RMI.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * Created by Csh on 2019/1/14.
 */
public class RandomLoadBanlance extends AbstractLoadBanlance {
    @Override
    protected String doSelect(List<String> repos) {
        int len = repos.size();
        Random random  = new Random();

        return repos.get(random.nextInt(len));
    }
}
