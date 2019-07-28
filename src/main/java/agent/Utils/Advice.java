package agent.Utils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by 64669 on 2019/7/26.
 */
public class Advice implements Serializable{

    public Class<?> targetClass;
    // 注意method是不能被序列化的，所以这就能解释arthas的源码里要自己再定义一个arthasMethod
    public String methodname;
    public Class<?>[] paramTypes;
    public String[] args; //存放着解析后的参数字符串

    public Advice(){

    }


    public Advice(Class<?> tc, String m, Class<?>[] pt, String[] ar){
        targetClass = tc;
        methodname = m;
        paramTypes = pt;
        args = ar;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

}
