package agent.CollectIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelCollect {

    /**
     * 采集方法的元数据
     * @param className 类名
     * @param methodName 方法名
     * @throws Exception
     */
    public static void Collect(String className, String methodName) throws Exception{
        Class<?> targetC = Class.forName(className);
        Method[] methods = targetC.getMethods();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(
                    new File("./"+className+"#"+methodName+"#"+"metaData")));
            out.writeUTF(targetC.getName());
            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    out.writeUTF(m.getName());
                    Class<?>[] paramTypes = m.getParameterTypes();
                    out.writeObject(paramTypes);
                    break; //只记录第一个获取到的方法，不考虑重构方法
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

}
