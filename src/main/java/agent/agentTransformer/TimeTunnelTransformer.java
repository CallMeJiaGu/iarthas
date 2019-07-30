package agent.agentTransformer;

import agent.CollectIO.TimeTunnelCollect;
import agent.Utils.Advice;
import agent.ASMDomain.IClassVisitor.TimeTunnelClassVisitor;
import agent.Utils.AdviceUtil;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassWriter;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelTransformer  implements ClassFileTransformer {

    public String className_i ;
    public String methodName_i ;

    public TimeTunnelTransformer(String cn , String mn){
        className_i = cn;
        methodName_i = mn;
    }


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        className = className.replace("/", ".");
        if (className.equals(className_i)) {

            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            try {
                //把要监控方法的类、方法名、方法参数类型保留在advice
                Advice advice = AdviceUtil.timeTunnelInitAdvice(className_i,methodName_i);
                TimeTunnelClassVisitor timeTunnelClassVisitor = new TimeTunnelClassVisitor(writer, methodName_i,advice);
                reader.accept(timeTunnelClassVisitor, ClassReader.SKIP_FRAMES);

                FileOutputStream fos = new FileOutputStream("F:/New.class");
                fos.write(writer.toByteArray());
                fos.close();

                return writer.toByteArray();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return classfileBuffer;
    }

}
