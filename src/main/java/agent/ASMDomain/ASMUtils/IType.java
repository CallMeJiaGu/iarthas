package agent.ASMDomain.ASMUtils;

/**
 * Created by 64669 on 2019/7/19.
 */
public class IType {

    public int loadOpcode; //加载该变量所需的Opcode
    public String desc; // 变量描述
    public String classType;    // 变量类型
    public int classTypeByteSize ; //占用变量栈的大小
    //  打印时所需的描述，在打印的时候，如果是一个Agent.test的类，是不能直接把Agent.test放在println中的desc中，
    // 而是需要把Java.lang.Object放上去
    public String printDesc ;


    public IType(int loadOpcode, String desc, String dataType, int byteSize,String pd) {
        this.loadOpcode = loadOpcode;
        this.desc = desc;
        this.classType = dataType;
        this.classTypeByteSize = byteSize;
        this.printDesc = pd;
    }

    public int getLoadOpcode() {
        return loadOpcode;
    }

    public void setLoadOpcode(int loadOpcode) {
        this.loadOpcode = loadOpcode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String dataType) {
        this.classType = dataType;
    }

    public int getClassTypeByteSize() {
        return classTypeByteSize;
    }

    public void setClassTypeByteSize(int classTypeByteSize) {
        this.classTypeByteSize = classTypeByteSize;
    }

    public String getPrintDesc() {
        return printDesc;
    }

    public void setPrintDesc(String printDesc) {
        this.printDesc = printDesc;
    }
}
