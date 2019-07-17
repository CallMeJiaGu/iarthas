package agent.agentDomain;

import agent.Main;
import agent.agentTransformer.ITransformer;

import java.lang.instrument.Instrumentation;

/**
 * Created by 64669 on 2019/7/12.
 */
public class IPreMain {
    public static void premain(String agentOps, Instrumentation inst) throws Exception{
        System.out.println("preAgent begin...");
        System.out.println("add transformer...");
        inst.addTransformer(new ITransformer());
    }
}
