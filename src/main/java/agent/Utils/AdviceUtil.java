package agent.Utils;

import java.lang.reflect.Method;

/**
 * Created by 64669 on 2019/7/28.
 */
public class AdviceUtil {

    public static Advice timeTunnelInitAdvice(String className, String methodName) {
        Advice advice = new Advice();
        try {
            // 保留类
            Class<?> tc = Class.forName(className);
            advice.setTargetClass(tc);

            Method[] methods = tc.getMethods();
            for (Method mTemp : methods) {
                if (mTemp.getName().equals(methodName)) {
                    // 保留方法
                    advice.setMethodname(mTemp.getName());
                    // 保留方法参数类型
                    advice.setParamTypes(mTemp.getParameterTypes());
                }
            }
            return advice;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
