package agent.agentDomain;


import agent.agentTransformer.RedefineTransformer;
import org.springframework.asm.ClassReader;
import java.lang.instrument.Instrumentation;



/**
 * Created by 64669 on 2019/7/12.
 */
public class IAgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws Exception {
        // agentArgs 为F:\Main.class 是一个字节码，注意不能仅仅根据路径来确定类名，而是要通过包中的路径+类名！！！
        String path = agentArgs;
        String targetClassName = getTargetClass(path).replace("/",".");
        inst.addTransformer(new RedefineTransformer(path,targetClassName), true);

        /**
         * 这段代码的意思是，重新转换目标类，也就是 Account 类。也就是说，你需要重新定义哪个类，需要指定，否则 JVM 不可能知道。
         * 还有一个类似的方法 redefineClasses ，注意，这个方法是在类加载前使用的。类加载后需要使用 retransformClasses 方法 */
        inst.retransformClasses(getClassByRedefinePath(targetClassName));
    }

    public static Class<?> getClassByRedefinePath(String targetClassName) throws Exception{
        return Class.forName(targetClassName);
    }

    public static String getTargetClass(String path){
        byte[] bytes = RedefineTransformer.getBytesFromFile(path);
        ClassReader classReader = new ClassReader(bytes);
        return classReader.getClassName();
    }

}
