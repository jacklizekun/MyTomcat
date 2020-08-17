package MyTomcat;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.StrUtil;
import MyTomcat.http.Request;
import MyTomcat.http.Response;
import MyTomcat.util.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author jacklizekun
 * @version 1.0
 * @date 2020/8/17
 **/
public class BootStrap {
	public static void main(String[] args) {
		try{
			/*绔彛鍙�*/
			int port=18080;
			/*妫�鏌ョ鍙ｆ槸鍚﹁鍗犵敤濡傛灉琚崰鐢ㄥ垯鎶涘嚭淇℃伅鍒版帶鍒跺彴*/
			if (!NetUtil.isUsableLocalPort(port)){
				System.out.println("18080绔彛琚崰鐢�");
				return;
			}
			/*閫氳繃Socket閫氫俊*/
			ServerSocket serverSocket=new ServerSocket(port);
			/*鎵撳紑杈撳叆杈撳嚭娴佸惊鐜帴鏀舵暟鎹ぇ灏忎负1024瀛楄妭*/
			while(true){
				Socket socket=serverSocket.accept();
				Request request=new Request(socket);
				System.out.println("娴忚鍣ㄧ殑杈撳叆淇℃伅:"+request.getRequestString());
				System.out.println("娴忚鍣ㄧ殑URI:"+request.getRequestUri());
				Response response=new Response();
				String string="鏈嶅姟绔搷搴�";
				response.getPrintWriter().println(string);
				handler200(socket, response);
				socket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void handler200(Socket socket,Response response) throws IOException {
		//寰楀埌鍝嶅簲鍐呭
		String contentType=response.getContentType();
		//寰楀埌鍝嶅簲娑堟伅澶�
		String header200= Constant.RESPONSE_HEAD_202;
		//杩炴帴鎴愬畬鏁寸殑http鍝嶅簲
		header200= StrUtil.format(header200, contentType);
		byte[] content=response.getBody();
		byte[] header=header200.getBytes();
		//杞崲鎴愬瓧鑺�,鎷兼帴瀛楄妭http鍝嶅簲
		byte[] headerContent=new byte[content.length+header.length];
		ArrayUtil.copy(header, 0, headerContent,0,header.length);
		ArrayUtil.copy(content, 0, headerContent, header.length,content.length );
		OutputStream outputStream=socket.getOutputStream();
		outputStream.write(headerContent);
		socket.close();
	}
}
