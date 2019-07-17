package agent.agentDomain;

import agent.agentTransformer.TraceTransformer;
import java.lang.instrument.Instrumentation;

/**
 * Created by 64669 on 2019/7/14.
 */
public class TraceAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws Exception {
        String methodName = agentArgs.substring(agentArgs.lastIndexOf(".")+1,agentArgs.length());
        String className = agentArgs.substring(0,agentArgs.lastIndexOf("."));
        inst.addTransformer(new TraceTransformer(className, methodName), true);

        inst.retransformClasses(getClassByRedefinePath(className));
    }

    public static Class<?> getClassByRedefinePath(String targetClassName) throws Exception{
        return Class.forName(targetClassName);
    }

}
