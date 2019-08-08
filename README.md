# iarthas
i_arthas 是模仿阿里开源的阿尔萨斯写的小工具。整体技术上采用 Java agent 对字节码的加载进 行拦截干涉，利用 ASM/ASM Byte Viewer 实现目标类字节码的加强，通过 Java attach 实现不同 虚拟机进程间的通信，做到了虚拟机层面上的 AOP。  

整体实现了简易版的 redefine（热替换）、 watch（方法监控）、trace（方法链路追踪）、timeTunnel（流量采集与回放）等功能。

### 实现思路


[阿尔萨斯 原理探究 仿写arthas-TimeTunnel 流量采集](https://www.callmejiagu.com/2019/07/28/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-%E4%BB%BF%E5%86%99arthas-TimeTunnel/)

[阿尔萨斯 原理探究 仿写arthas-watch](https://www.callmejiagu.com/2019/07/19/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-%E4%BB%BF%E5%86%99arthas-watch/)

[阿尔萨斯 原理探究 仿写arthas-trace](https://www.callmejiagu.com/2019/07/15/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-%E4%BB%BF%E5%86%99arthas-trace/)

[阿尔萨斯 原理探究 仿写arthas-redefine](https://www.callmejiagu.com/2019/07/14/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-%E4%BB%BF%E5%86%99arthas-redefine/)

[阿尔萨斯 原理探究 asm再认识（转载）](https://www.callmejiagu.com/2019/07/14/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-asm%E5%86%8D%E8%AE%A4%E8%AF%86/)

[阿尔萨斯 原理探究 agentmain实践](https://www.callmejiagu.com/2019/07/12/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-agentmain%E5%AE%9E%E8%B7%B5/)

[阿尔萨斯 原理探究 premain实践](https://www.callmejiagu.com/2019/07/12/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-premain%E5%AE%9E%E8%B7%B5/)

[阿尔萨斯 原理探究 预备知识](https://www.callmejiagu.com/2019/07/12/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-%E9%A2%84%E5%A4%87%E7%9F%A5%E8%AF%86/)

[阿尔萨斯 原理探究 初体验（转载）](https://www.callmejiagu.com/2019/07/09/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%88%9D%E4%BD%93%E9%AA%8C/)

### 大体效果

#### timetunnel 流量采集：

```
2
1
-------------------
开始采集方法：doAdd

2
开始采集方法：doDelet

1
-------------------
```

![](https://www.callmejiagu.com/2019/07/28/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF-%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6-%E4%BB%BF%E5%86%99arthas-TimeTunnel/1.png)

#### watch 方法监控

```
4
1
-------------------
监控当前方法：doAdd
 入参：
       参数类型-- int@1
       参数类型-- String@abc
       参数类型-- long@11
       参数类型-- Lagent/Job;@agent.Job@7adf9f5f
       参数类型-- Lagent/Main;@agent.Main@85ede7b
       参数类型-- double@0.11
 出参：
       参数类型-- int@4
4
1
-------------------
```

#### trace 方法子调用链路追踪

```
3
1
-------------------
doAdd
-|javalangThread@sleep
--|method  Cost: 1000.0282599999999 ms
-|agentJob@test
--|method  Cost: 1000.0648309999999 ms
almost time cost：
--|method  Cost: 2005.3643539999998 ms
3
1
-------------------
```

#### redefine 热加载

```
Hello World!
Bye Bye
-------------------
Hello World!
Bye Bye
-------------------
Hello World!
Bye Bye
fix good Bye!!
```




客户端代码：

```java
/**
 * Created by 64669 on 2019/7/11.
 */
public class Iarthas implements Serializable{

    public static void main(String[] args) throws Exception{
        timeTunnel("agent.Job.doAdd","57436");
    }


    public static void timeTunnel(String methodPath ,String pid)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(pid);
        virtualMachine.loadAgent("F:\\iarthas\\target\\agentTest-1.0-SNAPSHOT.jar", methodPath);
        virtualMachine.detach();
    }

    public static void watch(String methodPath ,String pid)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(pid);
        virtualMachine.loadAgent("C:\\Users\\64669\\Desktop\\arthas\\watch.jar", methodPath);
        virtualMachine.detach();
    }


    public static void trace(String methodPath ,String pid)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(pid);
        virtualMachine.loadAgent("C:\\Users\\64669\\Desktop\\arthas\\trace.jar", methodPath);
        virtualMachine.detach();
    }

    public static void redefine(String classPath, String pid)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(pid);
        virtualMachine.loadAgent("C:\\Users\\64669\\Desktop\\arthas\\redefine.jar", classPath);
        virtualMachine.detach();
    }
}

```

