package agent;

/**
 * Created by 64669 on 2019/7/14.
 */
public class Job {
    public int doAdd(int x,String s,long l ,Job j,Main n,double d) throws Exception{

        int xx = x+1;
        Thread.sleep(1000);
        test();
        doDelet(0,1);
        String a = "abc";
        test1(a);
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
}
