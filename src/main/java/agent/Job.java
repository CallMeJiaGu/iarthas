package agent;

import agent.Utils.Advice;
import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * Created by 64669 on 2019/7/14.
 */
public class Job  implements Serializable{

    public int x =1;
    public String str = "job";


    public int doAdd(int x,String s,long l ,Job j,Main n,double d){




        return  1;
    }

    public int doDelet(int x, int y)  {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("./classAdvice")));
            out.writeObject("");
        }catch (Exception e){}
        return x - y;
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
