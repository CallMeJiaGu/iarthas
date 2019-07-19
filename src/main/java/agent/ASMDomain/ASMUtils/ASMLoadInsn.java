package agent.ASMDomain.ASMUtils;

import static org.springframework.asm.Opcodes.*;

/**
 * Created by 64669 on 2019/7/19.
 */
public class ASMLoadInsn {

    public int x = ILOAD;

    public static int getOpcodes(String type){
        switch (type){
            case "int":
            case "short":
            case "boolean":
            case "byte":
            case "char":
                return ILOAD;
            case "long":
                return LLOAD;
            case "float":
                return FLOAD;
            case "double":
                return DLOAD;
            default:
                return ALOAD;
        }
    }

    /**
     * 局部变量部分和操作数栈部分中的每个槽（slot）可以保存除 long 和 double 变量之外的
     * 任意 Java 值。long 和 double 变量需要两个槽
     * @param type
     * @return
     */
    public static int getClassByteSize(String type){
        switch (type){
            case "int":
            case "short":
            case "boolean":
            case "byte":
            case "char":
            case "float":
                return 1;
            case "long":
            case "double":
                return 2;
            default:
                return 1;
        }

    }

}
