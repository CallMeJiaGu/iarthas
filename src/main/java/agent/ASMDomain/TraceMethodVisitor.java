package agent.ASMDomain;



import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.springframework.asm.Opcodes.GETSTATIC;
import static org.springframework.asm.Opcodes.INVOKESTATIC;


/**
 * Created by 64669 on 2019/7/14.
 */
public class TraceMethodVisitor extends MethodVisitor {

    public String name ;
    public int preCode = -1 ;
    public int lastCode = -1 ;

    protected TraceMethodVisitor(MethodVisitor mv, String n) {
        super(Opcodes.ASM5, mv);
        name = n;
    }

    @Override
    public void visitCode() {
        //方法体内开始时调用
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitLdcInsn(name);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "setStartTime", "(Ljava/lang/String;J)V", false);
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        // 每执行一个指令都会调用 , 如果是void方法的return应该是RETURN，如果是return int 那应该是IRETURN
        // 因为这里只是演示，所以线上的代码需要把返回的情况全部写一遍！！（FRETURN、DRETURN等等）
        // 这里可以使用范围表示是一个返回指令
        if (opcode <= Opcodes.RETURN && opcode >= Opcodes.IRETURN) {

            //输出最后一个方法的运行时间
            mv.visitLdcInsn(String.valueOf(lastCode));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "setEndTime", "(Ljava/lang/String;J)V", false);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(String.valueOf(lastCode));
            mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            // ----------------------
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("almost time cost：");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            //输出整体方法的时间
            mv.visitLdcInsn(name);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "setEndTime", "(Ljava/lang/String;J)V", false);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(name);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitMethodInsn(
            final int opcode,
            final String owner,
            final String name,
            final String descriptor,
            final boolean isInterface) {
        // SetEndTime , 第一次过滤掉，因为是没有方法体的
        if(preCode!=-1) {
            mv.visitLdcInsn(String.valueOf(preCode));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "setEndTime", "(Ljava/lang/String;J)V", false);

            // print Cost time
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(String.valueOf(preCode));
            mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        // 输出当前方法名
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("-|"+owner.replace("/","")+"@"+name);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // 当前方法set进去
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "agent/Utils/ITimeUtil", "setStartTime", "(Ljava/lang/String;J)V", false);

        preCode = lastCode = opcode;
        super.visitMethodInsn(opcode,owner,name,descriptor,isInterface);
    }

}
