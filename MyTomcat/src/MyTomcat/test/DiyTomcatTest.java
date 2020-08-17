package MyTomcat.test;

import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.StrUtil;
import MyTomcat.util.MiniBrowser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jacklizekun
 * @version 1.0
 * @date 2020/8/17
 **/
public class DiyTomcatTest {
	private static int port = 18080;
	private static String ip = "127.0.0.1";

	/**
	 * 使用Junit的@BeforeClass检测diyTomcat是否已经启动
	 **/
	@BeforeClass
	public static void beforeClass() {
		if (NetUtil.isUsableLocalPort(port)) {
			System.err.println("18080端口没有打开，请先打开MyTomcat");
			System.exit(1);
		} else {
			System.out.println("MyTomcat已经启动");
		}
	}

	/**
	 *使用Junit的@Test注解进行测试
	 **/
	@Test
	public void testDiyTomcat() {
		/*请求返回String类型的内容字符串*/
		String html = getContentString("/");
		/*利用Junit的Assert进行返回内容的对比*/
		Assert.assertEquals(html, "服务端响应");
	}

	/**
	 * 仿造浏览器请求
	 **/
	public String getContentString(String uri) {
		String url = StrUtil.format("http://{}:{}{}", ip, port, uri);
		return MiniBrowser.getContentString(url);
	}
}
