package agent.ASMDomain.ASMUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by 64669 on 2019/7/18.
 */
public class ASMTypeUtil {
    //TODO 数组类型没有判断
    static String[] basicClassTypes = {"boolean", "char", "byte", "short", "int", "float", "long", "double"};
    static String[] classTypes = {"boolean", "char", "byte", "short", "int", "float", "long", "double","Object" ,"String"};
    static String[] typeDesc = {"Z", "C", "B", "S", "I", "F", "J", "D", "Ljava/lang/Object;", "Ljava/lang/String;" };

    static String[] ZXTypes = {"java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short",
                                 "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double" };
    public static HashMap<String, String> typeMap = new HashMap<>(16);
    public static HashMap<String, String> ZXTypeMap = new HashMap<>(16);


    static {
        for(int i=0; i<classTypes.length ;i++){
            typeMap.put(typeDesc[i],classTypes[i]);
        }

        for(int i=0; i<8; i++){
            ZXTypeMap.put(basicClassTypes[i], ZXTypes[i]);
        }
    }


    /**
     * 判断一个数据是否为基本数据类型
     * @param type
     * @return
     */
    public static Boolean isBasicType(String type){
        if(Arrays.asList(basicClassTypes).contains(type))
            return true;
        return false;
    }

    public static String getTypeByDesc(String desc){
        if(typeMap.containsKey(desc))
            return typeMap.get(desc);
        return desc;
    }

    public static String getTypePrintDesc(String ct,String typeDescriptor){
        if(Arrays.asList(classTypes).contains(ct)){
            return typeDescriptor;
        }
        return "Ljava/lang/Object;";
    }

    /**
     * 获得装箱类型
     * @param classType
     * @return
     */
    public static String getZxType(String classType){
        if(ZXTypeMap.containsKey(classType))
            return ZXTypeMap.get(classType);
        return "Ljava/lang/Object";
    }

    /**
     * (IF)V
     * @param desc 方法的描述
     * @return 返回入参的类型的集合
     */
    public static ArrayList<IType> getInputParameterTypesByDesc(String desc){
        ArrayList<IType> parameterITypes = new ArrayList<>(10);

        org.springframework.asm.Type[] types = org.springframework.asm.Type.getArgumentTypes(desc);
       for(org.springframework.asm.Type type:types){
            // typeDescriptor的值为 I 或者 F 或者(Ljava/lang/String;) 等
            String typeDescriptor = type.getDescriptor();
             // 根据typeDescriptor或的对应的类型 int double
            String classType = getTypeByDesc(typeDescriptor);
            // 根据classType获得对应数据类型加载的opcode
            int opcode =  ASMLoadInsn.getOpcodes(classType);
            // 根据classType 获得数据类型占用的变量栈的大小
            int typeByteSize = ASMLoadInsn.getClassByteSize(classType);
            // 根据classType 和 typeDescriptor 决定输出的 desc
            String printDesc = getTypePrintDesc(classType,typeDescriptor);
            parameterITypes.add(new IType(opcode,typeDescriptor, classType, typeByteSize,printDesc));
        }
        return parameterITypes;
    }

    public static IType getOutputParameterTypesByDesc(String desc){
        org.springframework.asm.Type type = org.springframework.asm.Type.getReturnType(desc);
        String typeDescriptor = type.getDescriptor();
        // 根据typeDescriptor或的对应的类型 int double
        String classType = getTypeByDesc(typeDescriptor);
        // 根据classType获得对应数据类型加载的opcode
        int opcode =  ASMLoadInsn.getOpcodes(classType);
        // 根据classType 获得数据类型占用的变量栈的大小
        int typeByteSize = ASMLoadInsn.getClassByteSize(classType);
        // 根据classType 和 typeDescriptor 决定输出的 desc
        String printDesc = getTypePrintDesc(classType,typeDescriptor);
        return new IType(opcode,typeDescriptor,classType,typeByteSize,printDesc);
    }
}
