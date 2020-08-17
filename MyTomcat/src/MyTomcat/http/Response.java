package MyTomcat.http;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author jacklizekun
 * @version 1.0
 * @date 2020/8/17
 **/
public class Response {
	private StringWriter stringWriter;
	private PrintWriter printWriter;
	private String contentType;
	public Response(){
		this.contentType="text/html";
		this.stringWriter=new StringWriter();
		this.printWriter=new PrintWriter(stringWriter);
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public String getContentType() {
		return contentType;
	}
	public byte[] getBody(){
		String string=stringWriter.toString();
		return string.getBytes(StandardCharsets.UTF_8);
	}
}
