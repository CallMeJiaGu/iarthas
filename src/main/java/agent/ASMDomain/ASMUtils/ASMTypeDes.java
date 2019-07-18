package agent.ASMDomain.ASMUtils;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.HashMap;

/**
 * Created by 64669 on 2019/7/18.
 */
public class ASMTypeDes {
    //TODO 数组类型没有判断
    static String[] types = {"boolean", "char", "byte", "short", "int", "float", "long", "double","Object"};
    static String[] typeDesc = {"Z", "C", "B", "S", "I", "F", "J", "D", "Ljava/lang/Object" };
    public static HashMap<String, String> typeMap = new HashMap<>(16);

    static {
        for(int i=0; i<types.length ;i++){
            typeMap.put(types[i],typeDesc[i]);
        }
    }

    public static String getTypeByDesc(String desc){
        if(typeMap.containsKey(desc))
            return typeMap.get(desc);
        return "Error type";
    }
}
