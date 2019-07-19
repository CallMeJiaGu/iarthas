package agent.ASMDomain.IMethodVisitor;

import agent.ASMDomain.ASMUtils.IType;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import java.util.ArrayList;
import static org.springframework.asm.Opcodes.GETSTATIC;
import static org.springframework.asm.Opcodes.INVOKEVIRTUAL;

/**
 * Created by 64669 on 2019/7/18.
 */
public class WatchMethodVisitor extends MethodVisitor{

    public String name ;
    public ArrayList<IType> parInputITypes; //存放入参的类型Type
    public int lastVar ; // 存放最后一个变量出栈的位置，即为返回值的下标！！
    public IType parOutITypes; //存放输出类型
    public WatchMethodVisitor(MethodVisitor mv, String n,ArrayList<IType> ITypes,IType outITypes) {
        super(Opcodes.ASM5, mv);
        name = n;
        parInputITypes = ITypes;
        parOutITypes = outITypes;
    }

    @Override
    public void visitCode() {
        //
        // 输出当前方法名
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("监控当前方法："+name + "\n"+" 入参：");
        mv.visitMethodInsn(org.objectweb.asm.Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // 输出 入参的变量类型和与值
        if(parInputITypes !=null && parInputITypes.size()>0) {
            int index = 1;
            for (int i = 0; i < parInputITypes.size(); i++) {
                IType iType = parInputITypes.get(i);
                //打印变量类型
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("       参数类型-- "+iType.getClassType()+"@");
                mv.visitMethodInsn(org.objectweb.asm.Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
                //输出变量值
                /**
                 * 根据类型，String 返回 - ALOAD  ,int 返回 - ILOAD
                 * 根据类型来决定当前变量栈中的索引位置index
                 */
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitVarInsn(iType.getLoadOpcode(), index);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(" + iType.getPrintDesc() + ")V", false);
                index += iType.classTypeByteSize;
            }
        }
        super.visitCode();
    }


    @Override
    public void visitInsn(int opcode) {
        if (opcode <= Opcodes.RETURN && opcode >= Opcodes.IRETURN) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(" 出参：");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("       参数类型-- "+parOutITypes.getClassType()+"@");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(parOutITypes.getLoadOpcode(), lastVar);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(" + parOutITypes.getPrintDesc() + ")V", false);

        }
        super.visitInsn(opcode);
    }


    @Override
    public void visitVarInsn(int opcode, int var) {
        lastVar = var;
        super.visitVarInsn(opcode,var);
    }


}


