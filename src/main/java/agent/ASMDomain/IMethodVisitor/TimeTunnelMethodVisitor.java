package agent.ASMDomain.IMethodVisitor;

import agent.ASMDomain.ASMUtils.ASMTypeUtil;
import agent.ASMDomain.ASMUtils.IType;
import agent.Utils.Advice;
import org.springframework.asm.Label;
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

//        int outFileIndex = getObjectArrayIndex();
//        // 生成try catch
//        Label label0 = new Label();
//        Label label1 = new Label();
//        Label label2 = new Label();
//        mv.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
//
//        // 生成IO对象
//        mv.visitTypeInsn(NEW, "java/io/ObjectOutputStream");
//        mv.visitInsn(DUP);
//        mv.visitTypeInsn(NEW, "java/io/FileOutputStream");
//        mv.visitInsn(DUP);
//        mv.visitTypeInsn(NEW, "java/io/File");
//        mv.visitInsn(DUP);
//        mv.visitLdcInsn("./classAdvice");
//        mv.visitMethodInsn(INVOKESPECIAL, "java/io/File", "<init>", "(Ljava/lang/String;)V", false);
//        mv.visitMethodInsn(INVOKESPECIAL, "java/io/FileOutputStream", "<init>", "(Ljava/io/File;)V", false);
//        mv.visitMethodInsn(INVOKESPECIAL, "java/io/ObjectOutputStream", "<init>", "(Ljava/io/OutputStream;)V", false);
//        mv.visitVarInsn(ASTORE, outFileIndex);


        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("开始采集方法："+name + "\n");
        mv.visitMethodInsn(org.objectweb.asm.Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // 计算申请来的数组存放的位置
        int objectArrayIndex = getObjectArrayIndex()+1;
        if(parInputITypes !=null && parInputITypes.size()>0) {


            /**申请空间advice保留请求参数的object[]**/

            //object[] 数组大小为parInputITypes的大小
            mv.visitIntInsn(BIPUSH, parInputITypes.size());
            mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
            mv.visitVarInsn(ASTORE, objectArrayIndex);


            int index = 1;
            for (int i = 0; i < parInputITypes.size(); i++) {

                IType iType = parInputITypes.get(i);

                mv.visitVarInsn(ALOAD, objectArrayIndex);
                // object[]的下标
                mv.visitInsn(ICONST_0+i);
                // 局部变量的下标
                mv.visitVarInsn(iType.getLoadOpcode(), index);
                // 如果是8种常量类型，则需要调用String.valueOf的方法
                if(ASMTypeUtil.isBasicType(iType.classType)) {
                    String owner = ASMTypeUtil.getZxType(iType.classType);
                    String descriptor = "(" + iType.desc + ")L" + owner + ";";
                    mv.visitMethodInsn(INVOKESTATIC, owner, "valueOf", descriptor, false);
                }
                mv.visitMethodInsn(INVOKESTATIC, "com/alibaba/fastjson/JSON", "toJSONString", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                mv.visitInsn(AASTORE);

                index += iType.classTypeByteSize;

            }
        }


//        mv.visitVarInsn(ALOAD, outFileIndex);
//        mv.visitVarInsn(ALOAD, objectArrayIndex);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/ObjectOutputStream", "writeObject", "(Ljava/lang/Object;)V", false);

        super.visitCode();
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
