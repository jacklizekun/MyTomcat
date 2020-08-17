package MyTomcat;

import MyTomcat.classloader.CommonClassLoader;

import java.lang.reflect.Method;
/**
 * 
 * @author jacklizekun
 *
 */
public class Bootstrap {
    public static void main(String[] args) throws Exception {
    	//自定义加载类
        CommonClassLoader commonClassLoader = new CommonClassLoader();
        //线程加载
        Thread.currentThread().setContextClassLoader(commonClassLoader);
        //加载的类名称
        String serverClassName = "MyTomcat.catalina.Server";
        //实例化对象
        Class<?> serverClazz = commonClassLoader.loadClass(serverClassName);

        Object serverObject = serverClazz.newInstance();
        //启动方法
        Method m = serverClazz.getMethod("start");
        
        m.invoke(serverObject);

        // 不能关闭，否则后续就不能使用
        // commonClassLoader.close();

    }
}