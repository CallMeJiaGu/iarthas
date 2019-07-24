package agent.agentDomain;

import agent.CollectIO.TimeTunnelCollect;
import agent.agentTransformer.TimeTunnelTransformer;
import agent.agentTransformer.WatchTransformer;

import java.lang.instrument.Instrumentation;

/**
 * 采集一个方法出参、入参的结果 --先不考虑子调用过程
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws Exception {
        String methodName = agentArgs.substring(agentArgs.lastIndexOf(".")+1,agentArgs.length());
        String className = agentArgs.substring(0,agentArgs.lastIndexOf("."));

        /** 记录类名、方法名、参数 -- 这里在attach来的时候，是没有传递参数的，因此我这里做的是对所有同名的方法进行操作
         *  即没有考虑重载方法的验证
         */
        TimeTunnelCollect.Collect(className,methodName);

        inst.addTransformer(new TimeTunnelTransformer(className, methodName), true);
        inst.retransformClasses(getClassByRedefinePath(className));
    }

    public static Class<?> getClassByRedefinePath(String targetClassName) throws Exception{
        return Class.forName(targetClassName);
    }



}
