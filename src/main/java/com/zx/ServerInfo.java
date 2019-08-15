package com.zx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

public class ServerInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfo.class);

    public static void saveSystemProperty() {
        try {
            Runtime r = Runtime.getRuntime();
            Properties props = System.getProperties();
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            Map<String, String> map = System.getenv();

            File file=new File("logs/serverinfo.log");
            if (!file.exists()) {
                file.getParentFile().mkdir();
            }
            FileOutputStream fOutputStream = new FileOutputStream(file, false);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOutputStream, "UTF-8");
            BufferedWriter out = new BufferedWriter(outputStreamWriter);
            String userName = map.get("USER");// 获取用户名
            String computerName = map.get("HOSTNAME");// 获取计算机名
            String userDomain = map.get("USERDOMAIN");// 获取计算机域名
            out.write("用户名:"+ userName + "\r\n");
            out.write("计算机名:"+ computerName + "\r\n");
            out.write("计算机域名:"+ userDomain + "\r\n");
            out.write("本地ip地址:"+ ip + "\r\n");
            out.write("本地主机名:"+ addr.getHostName() + "\r\n");
            out.write("JVM可以使用的总内存:"+ r.totalMemory() + "\r\n");
            out.write("JVM可以使用的剩余内存:"+ r.freeMemory() + "\r\n");
            out.write("JVM可以使用的处理器个数:"+ r.availableProcessors() + "\r\n");
            out.write("Java的运行环境版本："+ props.getProperty("java.version") + "\r\n");
            out.write("Java的安装路径："+ props.getProperty("java.home") + "\r\n");
            out.write("Java的虚拟机规范版本："+ props.getProperty("java.vm.specification.version") + "\r\n");
            out.write("Java的虚拟机实现版本："+ props.getProperty("java.vm.version") + "\r\n");
            out.write("Java的虚拟机实现名称："+ props.getProperty("java.vm.name") + "\r\n");
            out.write("Java运行时环境规范版本："+ props.getProperty("java.specification.version") + "\r\n");
            out.write("Java的类格式版本号："+ props.getProperty("java.class.version") + "\r\n");
            out.write("Java的类路径："+ props.getProperty("java.class.path") + "\r\n");
            out.write("加载库时搜索的路径列表："+ props.getProperty("java.library.path") + "\r\n");
            out.write("默认的临时文件路径："+ props.getProperty("java.io.tmpdir") + "\r\n");
            out.write("一个或多个扩展目录的路径："+ props.getProperty("java.ext.dirs") + "\r\n");
            out.write("操作系统的名称："+ props.getProperty("os.name") + "\r\n");
            out.write("操作系统的构架："+ props.getProperty("os.arch") + "\r\n");
            out.write("操作系统的版本："+ props.getProperty("os.version") + "\r\n");
            out.write("文件分隔符："+ props.getProperty("file.separator") + "\r\n");
            out.write("路径分隔符："+ props.getProperty("path.separator") + "\r\n");
            out.write("用户的账户名称："+ props.getProperty("user.name") + "\r\n");
            out.write("用户的主目录："+ props.getProperty("user.home") + "\r\n");
            out.write("用户的当前工作目录："+ props.getProperty("user.dir") + "\r\n");

            out.write("HOSTNAME:"+ map.get("HOSTNAME") + "\r\n");
            out.write("LOGNAME:"+ map.get("LOGNAME") + "\r\n");
            out.write("PWD:"+ map.get("PWD") + "\r\n");
            out.write("JAVA_HOME:"+ map.get("JAVA_HOME") + "\r\n");
            out.write("CLASSPATH:"+ map.get("CLASSPATH") + "\r\n");
            out.write("TSS_SDK_PATH:"+ map.get("TSS_SDK_PATH") + "\r\n");
            out.write("LD_LIBRARY_PATH:"+ map.get("LD_LIBRARY_PATH") + "\r\n");
            out.write("JRE_HOME:"+ map.get("JRE_HOME") + "\r\n");
            out.write("LANG:"+ map.get("LANG") + "\r\n");
            out.write("HOME:"+ map.get("HOME") + "\r\n");
            out.write("PATH:"+ map.get("PATH") + "\r\n");

            out.close();
            outputStreamWriter.close();
            fOutputStream.close();
        } catch (Exception e) {
            LOGGER.error("saveSystemProperty->msg={}", e.getMessage(), e);
        }
    }

}
