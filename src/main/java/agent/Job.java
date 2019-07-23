package agent;

/**
 * Created by 64669 on 2019/7/14.
 */
public class Job {

    volatile int A =1;

    public synchronized int doAdd(int x,String s,long l ,Job j,Main n,double d) throws Exception{
        int aa = 1;
        int bb = 2;
        int xx = x+3;
        int x1 = 1;
        Thread.sleep(1000);
        test();
        doDelet(0,1);
        String a = "abc";
        test1(a);
        int x11 = 1;
        return  xx;

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

    public String test1(String str){
        return str;
    }

    public void add(int x,int y){
        int result = x+y;
        System.out.println(result);
    }
}
