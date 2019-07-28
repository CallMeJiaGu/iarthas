package agent.ASMDomain.IMethodVisitor;

import agent.ASMDomain.ASMUtils.ASMTypeUtil;
import agent.ASMDomain.ASMUtils.IType;
import agent.Utils.Advice;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import java.util.ArrayList;

import static org.springframework.asm.Opcodes.*;

/**
 * Created by 64669 on 2019/7/24.
 */
public class TimeTunnelMethodVisitor extends MethodVisitor {

    public String name ;
    public ArrayList<IType> parInputITypes; //存放入参的类型Type
    public int lastVar ; // 存放最后一个变量出栈的位置，即为返回值的下标！！
    public IType parOutITypes; //存放输出类型
    public Advice advice;

    public TimeTunnelMethodVisitor(MethodVisitor mv, String n,ArrayList<IType> ITypes,Advice ad) {
        super(Opcodes.ASM5, mv);
        name = n;
        parInputITypes = ITypes;
        advice = ad;
    }

    @Override
    public void visitCode() {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("开始采集方法："+name + "\n");
        mv.visitMethodInsn(org.objectweb.asm.Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        if(parInputITypes !=null && parInputITypes.size()>0) {


            /**申请空间advice保留请求参数的object[]**/

            //object[] 数组大小为parInputITypes的大小
            mv.visitIntInsn(BIPUSH, parInputITypes.size());
            mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
            // 计算申请来的数组存放的位置
            int objectArrayIndex = getObjectArrayIndex();
            mv.visitVarInsn(ASTORE, objectArrayIndex);


            int index = 1;
            for (int i = 0; i < parInputITypes.size(); i++) {

                IType iType = parInputITypes.get(i);

                mv.visitVarInsn(ALOAD, objectArrayIndex);
                // object[]的下标
                mv.visitInsn(ICONST_0+i);
                // 局部变量的下标
                mv.visitVarInsn(ILOAD, i);
                // 如果是8种常量类型，则需要调用String.valueOf的方法
                if(ASMTypeUtil.isBasicType(iType.classType)) {
                    String owner = ASMTypeUtil.getZxType(iType.classType);
                    String descriptor = "(" + iType.desc + ")" + owner + ";";
                    mv.visitMethodInsn(INVOKESTATIC, owner, "valueOf", descriptor, false);
                }
                mv.visitMethodInsn(INVOKESTATIC, "com/alibaba/fastjson/JSON", "toJSONString", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                mv.visitInsn(AASTORE);

            }
        }

    }




    @Override
    public void visitVarInsn(int opcode, int var) {
        lastVar = var;
        super.visitVarInsn(opcode,var);
    }


    public int getObjectArrayIndex(){
        int x = 0;
        for(int i=0; i<parInputITypes.size(); i++) {
            x +=parInputITypes.get(i).classTypeByteSize;
        }
        return x+1;
    }

}
