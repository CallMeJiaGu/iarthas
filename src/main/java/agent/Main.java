package agent;

import java.io.Serializable;

/**
 * Created by 64669 on 2019/7/12.
 */
public class Main {

    public int x = 1;
    public int y = 2;
    public Job j = new Job();

    public static void main( String[] args ) throws Exception {


//        String clazz = Thread.currentThread() .getStackTrace()[1].getClassName();
//        // 获得当前方法名
//        String method = Thread.currentThread() .getStackTrace()[1].getMethod();
//        System.out.println(clazz+method);

        Job demo = new Job();
        Main m = new Main();
        while (true) {
            //demo.doAdd(0,1);
            System.out.println(demo.doAdd(1,"abc",11L,demo,m,0.11d));
            System.out.println(demo.doDelet(2,1));
            System.out.println("-------------------");
            Thread.sleep(8000);
        }


    }
}
