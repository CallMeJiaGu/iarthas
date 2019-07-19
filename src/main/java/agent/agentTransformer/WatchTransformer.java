package agent.agentTransformer;

import agent.ASMDomain.IClassVisitor.TraceClassVisitor;
import agent.ASMDomain.IClassVisitor.WatchClassVisitor;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassWriter;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by 64669 on 2019/7/18.
 */
public class WatchTransformer implements ClassFileTransformer {

    public String className_i ;
    public String methodName_i ;

    public WatchTransformer(String cn , String mn){
        className_i = cn;
        methodName_i = mn;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        if(className.equals(className_i)) {
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(reader,ClassWriter.COMPUTE_MAXS);

            WatchClassVisitor watchClassVisitor = new WatchClassVisitor(writer,methodName_i);
            reader.accept(watchClassVisitor, ClassReader.SKIP_FRAMES);

            // 打印转换字节码结果
            try {
                FileOutputStream fos = new FileOutputStream("F:/New.class");
                fos.write(writer.toByteArray());
                fos.close();
            }catch (Exception e){
                System.out.println(e);
            }


            return writer.toByteArray();
        }
        return classfileBuffer;
    }
}
