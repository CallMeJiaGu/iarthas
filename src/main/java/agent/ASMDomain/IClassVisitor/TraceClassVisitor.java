package agent.ASMDomain.IClassVisitor;



import agent.ASMDomain.IMethodVisitor.TraceMethodVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.ClassVisitor;

import static org.springframework.asm.Opcodes.ASM5;


/**
 * Created by 64669 on 2019/7/14.
 */
public class TraceClassVisitor extends ClassVisitor {

    public String methodName ;

    public TraceClassVisitor(final ClassVisitor cv, String mn) {
        super(ASM5, cv);
        methodName = mn;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(methodName)) {
            return new TraceMethodVisitor(mv,name);
        }
        return mv;
    }
}
