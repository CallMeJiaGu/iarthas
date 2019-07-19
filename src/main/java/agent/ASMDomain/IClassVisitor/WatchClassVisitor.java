package agent.ASMDomain.IClassVisitor;

import agent.ASMDomain.ASMUtils.ASMTypeUtil;
import agent.ASMDomain.ASMUtils.IType;
import agent.ASMDomain.IMethodVisitor.WatchMethodVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;

import java.util.ArrayList;

import static org.springframework.asm.Opcodes.ASM5;

/**
 * Created by 64669 on 2019/7/18.
 */
public class WatchClassVisitor  extends ClassVisitor {
    public String methodName ;

    public WatchClassVisitor(final ClassVisitor cv, String mn) {
        super(ASM5, cv);
        methodName = mn;
    }

    /**
     * 可以根据Desc获取入参的类型，desc的值可以参考下面
     * void m(int i, float f) 的Description：(IF)V
     * int[] m(int i, String s) 的Description：(ILjava/lang/String;)[I
     * int m(Object o) 的Description：(Ljava/lang/Object;)I
     * Object m(int[] i) 的Description：([I)Ljava/lang/Object;
     */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(methodName)) {
            ArrayList<IType> parInputITypes = ASMTypeUtil.getInputParameterTypesByDesc(desc);
            IType parOutputTypes = ASMTypeUtil.getOutputParameterTypesByDesc(desc);
            return new WatchMethodVisitor(mv,name, parInputITypes,parOutputTypes);
        }
        return mv;
    }

}
