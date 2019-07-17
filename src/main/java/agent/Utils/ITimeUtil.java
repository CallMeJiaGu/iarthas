package agent.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 64669 on 2019/7/14.
 */
public class ITimeUtil {
    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime = new HashMap<>();
    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
    }
    public static void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
    }
    public static String getCostTime(String methodName) {
        if("-1".equals(methodName)){
            return "";
        }
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        double result =  Long.valueOf(end - start)*0.000001;
        return "--|method  Cost: " + String.valueOf(result)+ " ms";
    }
}
