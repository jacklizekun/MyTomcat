package MyTomcat.catalina;
 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 
import MyTomcat.http.Request;
import MyTomcat.http.Response;
import MyTomcat.util.ThreadPoolUtil;
import cn.hutool.log.LogFactory;

//接受Socket请求
public class Connector implements Runnable {
    int port;
    private Service service;

    //压缩文件的相关信息
    private String compression;
    private int compressionMinSize;
    private String noCompressionUserAgents;
    private String compressableMimeType;
    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public int getCompressionMinSize() {
        return compressionMinSize;
    }

    public void setCompressionMinSize(int compressionMinSize) {
        this.compressionMinSize = compressionMinSize;
    }

    public String getNoCompressionUserAgents() {
        return noCompressionUserAgents;
    }

    public void setNoCompressionUserAgents(String noCompressionUserAgents) {
        this.noCompressionUserAgents = noCompressionUserAgents;
    }

    public String getCompressableMimeType() {
        return compressableMimeType;
    }

    public void setCompressableMimeType(String compressableMimeType) {
        this.compressableMimeType = compressableMimeType;
    }
    public Connector(Service service) {
        this.service = service;
    }
 
    public Service getService() {
        return service;
    }
 
    public void setPort(int port) {
        this.port = port;
    }
 
    @Override
    public void run() {
        try {
        	//监听端口
            ServerSocket ss = new ServerSocket(port);
            while(true) {
            	//等待发送socket
                Socket s = ss.accept();
                //新建线程
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                        	//封装request对象
                            Request request = new Request(s, Connector.this);
                            //新建响应对象
                            Response response = new Response();
                            //HttP流程
                            HttpProcessor processor = new HttpProcessor();
                            //执行过程
                            processor.execute(s,request, response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (!s.isClosed())
                            try {
                                s.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                //线程池运行
                ThreadPoolUtil.run(r);
            }

        } catch (IOException e) {
            LogFactory.get().error(e);
            e.printStackTrace();
        }
    }
 
    public void init() {
    	//初始化输出
        LogFactory.get().info("Initializing ProtocolHandler [http-bio-{}]",port);
    }
 
    public void start() {
        LogFactory.get().info("Starting ProtocolHandler [http-bio-{}]",port);
        new Thread(this).start();
    }
 

}