package agent;

import agent.Utils.Advice;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.lang.reflect.Method;

import static java.lang.Class.forName;

/**
 * Created by 64669 on 2019/7/23.
 */

/**
 * 保留下的数据首先要用对应方法的参数列表，类名，这样反射可以找到
 * 还要对类的参数 和 data分开存储
 */

public class TimeTunnelTest {

    public int x =5;
    public Main main1 = new Main();
    public int add(TimeTunnelTest x,int y){
        return x.x+y;
    }


    public static void main(String[] args) throws Exception{
//        TimeTunnelTest t = new TimeTunnelTest();
//        t.x = 4;
//        while(true){
//            func("abc", 1, 0.1d, t);
//            Thread.sleep(1000);
//        }
        String s = JSONObject.toJSONString(1);
        //System.out.println(s);

        Object o = JSONObject.parseObject("1",Integer.class);
        //testWrite();
        testRead();
        //TimeTunnelTest timeTunnelTest = new TimeTunnelTest();
        //func(1,"123",0.1d,timeTunnelTest);
    }

    public static int func(int x , String str , double d ,TimeTunnelTest t){
//        String result = JSONObject.toJSONString(t);
//        System.out.println(t.x);
//        System.out.println(result);
//
//        String s = "{\"main1\":{\"j\":{\"str\":\"job\",\"x\":2},\"x\":1,\"y\":2},\"x\":5}";
//        TimeTunnelTest t1 = JSON.parseObject(s,TimeTunnelTest.class);
//        System.out.println(t1.x);
        return x+t.x;
    }

    public static void testFastJson(TimeTunnelTest t) {
        String result = "[\"abc\", 1, 0.1, {\"main1\":{\"x\":1,\"y\":2},\"x\":4}]";
        JSONObject object = JSON.parseObject(result);
        object.getJSONObject("");
        object.getJSONObject("str");
        System.out.println(JSONObject.toJSONString(t));
    }



    public static void testWrite() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(new File("./classAdvice")));
            // 保留类
            Class<?> tc = TimeTunnelTest.class;

            Method[] methods = tc.getMethods();
            for (Method mTemp : methods) {
                if (mTemp.getName().equals("func")) {

                    // 保留方法
                    String mn = mTemp.getName();

                    // 保留请求参数
                    Class<?>[] pt = mTemp.getParameterTypes();

                    // 保留参数
                    String str = "abc";
                    int x = 10;
                    double d = 0.11d;
                    TimeTunnelTest timeTunnelTest = new TimeTunnelTest();
                    timeTunnelTest.x = 1;
                    String[] args = new String[4];
                    args[0] = JSON.toJSONString(x);
                    args[1] = JSON.toJSONString(str);
                    args[2] = JSON.toJSONString(d);
                    args[3] = JSON.toJSONString(timeTunnelTest);

                    Advice advice = new Advice(tc,mn,pt,args);
                    out.writeObject(advice);
                    break;
                }

            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            try {
                if (out != null)
                    out.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }

    }

    public static void testRead(){
        ObjectInputStream input = null;
        try{
            input = new ObjectInputStream(new FileInputStream(new File("./classAdvice")));
            Advice advice = (Advice) input.readObject();

            Class<?> targetClass = advice.targetClass;
            Method method = targetClass.getMethod(advice.methodname,advice.paramTypes);
            Object[] args = getParams(advice.args);
            Object result = method.invoke(targetClass.newInstance(),args);
            System.out.println((int)result);

        }catch (Exception e){
            System.out.println(e);
        }finally {
            try {
                if (input != null)
                    input.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }


    public static Object[] getParams(String[] strAgrs){
        Object[] result = new Object[strAgrs.length];
//        for(int i=0; i<strAgrs.length ;i++){
//            result[i] = JSON.parseObject(strAgrs[i]);
//        }
        result[0] = JSON.parseObject(strAgrs[0],Integer.class);
        result[1] = JSON.parseObject(strAgrs[1], String.class);
        result[2] = JSON.parseObject(strAgrs[2], Double.class);
        result[3] = JSON.parseObject(strAgrs[3], TimeTunnelTest.class);

        return result;
    }


    public static void test(){
        String jsonS = "{\"j\":{\"str\":\"job\",\"x\":1},\"x\":1,\"y\":\"abc\"}";
        JSONObject object = JSON.parseObject(jsonS);

        System.out.println(object.get("j"));
    }

}
