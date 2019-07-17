package agent.agentTransformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by 64669 on 2019/7/12.
 */
public class ITransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        className = className.replace("/", ".");
        // 对agent.Main类中所有申明的方法进行加强
        if (className.equals("agent.Main")) {
            try {
                CtClass ctClass = ClassPool.getDefault().get(className);
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();
                for (CtMethod method : ctMethods) {
                    method.insertBefore("System.out.println(\"method begin time:\"+System.currentTimeMillis());");
                    method.insertAfter("System.out.println(\"method end time:\"+System.currentTimeMillis());");
                }
                return ctClass.toBytecode();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return new byte[0];
    }
}

