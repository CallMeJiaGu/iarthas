package agent;

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

    public int add(int x,int y){
        //System.out.println(x+y);
        return x+y;
    }

//    public void add(int x,TimeTunnelTest t){
//        System.out.println("x");
//    }


    public static void main(String[] args) throws Exception{
        //testWrite();
        testRead();
        /**
        output.writeUTF(target.getName());
        output.writeUTF(method.getName());
        output.writeObject(method.getParameterTypes());
         **/


        /**
        String className = objectInputStream.readUTF();
        String methodName = objectInputStream.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
        Object[] arguments = (Object[]) objectInputStream.readObject();
         **/

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

            Class<?> targetClass = Class.forName(classname);
            Method method = targetClass.getMethod(methodName,paramTypes);
            Object[] args = new Object[2];
            args[0] = 1;
            args[1] = 2;
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

}
