package agent.ASMDomain.IMethodVisitor;

import agent.ASMDomain.ASMUtils.IType;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import java.util.ArrayList;

/**
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelMethodVisitor extends MethodVisitor {

    public String name ;
    public ArrayList<IType> parInputITypes; //存放入参的类型Type
    public int lastVar ; // 存放最后一个变量出栈的位置，即为返回值的下标！！
    public IType parOutITypes; //存放输出类型
    public TimeTunnelMethodVisitor(MethodVisitor mv, String n,ArrayList<IType> ITypes,IType outITypes) {
        super(Opcodes.ASM5, mv);
        name = n;
        parInputITypes = ITypes;
        parOutITypes = outITypes;
    }

    @Override
    public void visitCode() {

    }


    @Override
    public void visitVarInsn(int opcode, int var) {
        lastVar = var;
        super.visitVarInsn(opcode,var);
    }

}
