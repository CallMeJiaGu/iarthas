package agent;

/**
 * Created by 64669 on 2019/7/14.
 */
public class Job {
    public int doAdd(int x, int y) throws Exception{
        Thread.sleep(1000);
        test();
        return  x+y;

    }

    public int doDelet(int x, int y) throws Exception{
        Thread.sleep(500);
        return x-y;
    }

    public void test() throws Exception{
        StackTraceElement[] stackTraceElement =  Thread.currentThread().getStackTrace();
        Thread.sleep(1000);
        //System.out.println("");
    }
}
