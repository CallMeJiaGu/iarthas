package agent.ASMDomain.IMethodVisitor;

import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

/**
 * Created by 64669 on 2019/7/18.
 */
public class WatchMethodVisitor extends MethodVisitor{

    public String name ;

    public WatchMethodVisitor(MethodVisitor mv, String n) {
        super(Opcodes.ASM5, mv);
        name = n;
    }

    @Override
    public void visitCode() {

    }

    @Override
    public void visitInsn(int opcode) {

    }
}


