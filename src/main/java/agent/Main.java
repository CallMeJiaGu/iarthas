package agent;

import java.util.concurrent.ExecutionException;

/**
 * Created by 64669 on 2019/7/12.
 */
public class Main {

    public static void main( String[] args ) throws Exception {

        Job demo = new Job();
        Main m = new Main();
        while (true) {
            //demo.doAdd(0,1);
            System.out.println(demo.doAdd(1,"abc",11L,demo,m,0.11d));
            System.out.println(demo.doDelet(2,1));
            System.out.println("-------------------");
            Thread.sleep(1000);
        }
    }
}
