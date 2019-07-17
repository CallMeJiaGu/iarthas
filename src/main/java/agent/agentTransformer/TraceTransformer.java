package agent.agentTransformer;

import agent.ASMDomain.TraceClassVisitor;

import org.springframework.asm.ClassWriter;
import org.springframework.asm.ClassReader;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;



/**
 * Created by 64669 on 2019/7/14.
 */
public class TraceTransformer implements ClassFileTransformer {

    public String className_i ;
    public String methodName_i ;

    public TraceTransformer(String cn , String mn){
        className_i = cn;
        methodName_i = mn;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        className = className.replace("/", ".");

        if(className.equals(className_i)){
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            TraceClassVisitor traceClassVisitor = new TraceClassVisitor(writer,methodName_i);
            reader.accept(traceClassVisitor, ClassReader.SKIP_FRAMES);

            return writer.toByteArray();
        }
        return classfileBuffer;
    }
}
