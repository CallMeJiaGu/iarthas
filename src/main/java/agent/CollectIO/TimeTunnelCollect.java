package agent.CollectIO;

import agent.Utils.Advice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelCollect {

    /**
     *
     * @param advice
     * @param path
     * @throws Exception
     */
    public static void collect(Advice advice, String path) throws Exception{
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(path)));
            out.writeObject(advice);
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
