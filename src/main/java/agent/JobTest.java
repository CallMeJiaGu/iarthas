package agent;

import agent.Utils.ITimeUtil;
import ch.qos.logback.core.util.TimeUtil;

/**
 * Created by 64669 on 2019/7/14.
 */
public class JobTest {

    public int doAdd(int x, int y) throws Exception{
        ITimeUtil.setStartTime("Fuc1",System.nanoTime());
        Thread.sleep(1000); //模拟耗时操作
        ITimeUtil.setEndTime("Fuc1",System.currentTimeMillis());
        System.out.println(ITimeUtil.getCostTime("Fuc1"));
        return  x+y;
    }

    public int doDelet(int x, int y) throws Exception{
        ITimeUtil.setStartTime("Fuc2",System.currentTimeMillis());
        Thread.sleep(500);
        ITimeUtil.setEndTime("Fuc2",System.currentTimeMillis());
        System.out.println(ITimeUtil.getCostTime("Fuc2"));
        return x-y;
    }

}
