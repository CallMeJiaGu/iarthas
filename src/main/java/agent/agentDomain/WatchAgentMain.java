package agent.agentDomain;

import agent.agentTransformer.RedefineTransformer;
import agent.agentTransformer.TraceTransformer;
import agent.agentTransformer.WatchTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Created by 64669 on 2019/7/18.
 * 监控一个方法的执行时间、入参、出参
 */
public class WatchAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws Exception {
        String methodName = agentArgs.substring(agentArgs.lastIndexOf(".")+1,agentArgs.length());
        String className = agentArgs.substring(0,agentArgs.lastIndexOf("."));

        inst.addTransformer(new WatchTransformer(className, methodName), true);
        inst.retransformClasses(getClassByRedefinePath(className));
    }

    public static Class<?> getClassByRedefinePath(String targetClassName) throws Exception{
        return Class.forName(targetClassName);
    }
}
