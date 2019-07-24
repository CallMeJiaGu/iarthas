package agent.ASMDomain.IClassVisitor;

import agent.ASMDomain.ASMUtils.ASMTypeUtil;
import agent.ASMDomain.ASMUtils.IType;
import agent.ASMDomain.IMethodVisitor.TimeTunnelMethodVisitor;
import agent.ASMDomain.IMethodVisitor.WatchMethodVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;

import java.util.ArrayList;

import static org.springframework.asm.Opcodes.ASM5;

/**
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelClassVisitor extends ClassVisitor {

    public String methodName ;

    public TimeTunnelClassVisitor(final ClassVisitor cv, String mn) {
        super(ASM5, cv);
        methodName = mn;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(methodName)) {
            ArrayList<IType> parInputITypes = ASMTypeUtil.getInputParameterTypesByDesc(desc);
            IType parOutputTypes = ASMTypeUtil.getOutputParameterTypesByDesc(desc);
            return new TimeTunnelMethodVisitor(mv,name, parInputITypes,parOutputTypes);
        }
        return mv;
    }

}
