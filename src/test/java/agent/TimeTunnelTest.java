package agent;

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

public class TimeTunnelTest implements Serializable{

    public int x =5;
    public int add(TimeTunnelTest x,int y){
        return x.x+y;
    }


    public static void main(String[] args) throws Exception{
        //testWrite();
        test();
        //testRead();
    }

    public static void testWrite() {
        Method[] methods =  TimeTunnelTest.class.getMethods();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(new File("./classTest")));
            System.out.println("写入类名");
            out.writeUTF(TimeTunnelTest.class.getName());
            for (Method m : methods) {
                if (m.getName().equals("add")) {
                    System.out.println("写入方法名");
                    out.writeUTF(m.getName());
                    Class<?>[] paramTypes = m.getParameterTypes();
                    System.out.println("写入参数");
                    out.writeObject(paramTypes);

                    // 是不是可以提供一个类，专门来集成，这个类已经实现了serizable的 ,
                    // 这样好像不能是实现，因为不能确保每个子类都继承了serizable的
                    //TimeTunnelTest timeTunnelTest = new TimeTunnelTest();
                    Object[] args = new Object[2];
                    args[0] = 1;
                    args[1] = 2;
                    out.writeObject(args);
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
            input = new ObjectInputStream(new FileInputStream(new File("./classTest")));
            String classname = input.readUTF();
            String methodName = input.readUTF();
            Class<?>[] paramTypes = (Class<?>[])input.readObject();
            Object[] args = (Object[]) input.readObject();

            Class<?> targetClass = Class.forName(classname);
            Method method = targetClass.getMethod(methodName,paramTypes);
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

        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("abc");

        threadLocal.get();


        ThreadLocal threadLoca2 = new ThreadLocal();
        threadLoca2.set("123");

    }

    public static void test(){
        String jsonS = "{\"j\":{\"str\":\"job\",\"x\":1},\"x\":1,\"y\":\"abc\"}";
        JSONObject object = JSON.parseObject(jsonS);

        System.out.println(object.get("j"));
    }

}
