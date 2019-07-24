package agent.agentTransformer;

import agent.ASMDomain.IClassVisitor.TimeTunnelClassVisitor;
import agent.ASMDomain.IClassVisitor.WatchClassVisitor;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassWriter;

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

            TimeTunnelClassVisitor timeTunnelClassVisitor = new TimeTunnelClassVisitor(writer, methodName_i);
            reader.accept(timeTunnelClassVisitor, ClassReader.SKIP_FRAMES);
            return writer.toByteArray();
        }
        return classfileBuffer;
    }

}
