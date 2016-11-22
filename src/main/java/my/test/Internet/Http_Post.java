package my.test.Internet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class Http_Post {
	// 请求服务器端的url

	private static URL url;

	/**
	 * @param params
	 *            填写的url的参数
	 * @param encode
	 *            字节编码
	 * @return
	 * @throws Exception 
	 */
	public static String sendPostMessage(String urlsString, Map<String, String> params, String encode) throws Exception {
		if (encode == null || encode.equals("")) {
			encode = "utf-8";
		}

		url = new URL(urlsString);

		// 作为StringBuffer初始化的字符串
		StringBuffer buffer = new StringBuffer();

		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				buffer.append("" + entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			buffer.deleteCharAt(buffer.length() - 1);
		}

		//System.out.println("-->>" + buffer.toString());
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(3000);
		urlConnection.setRequestMethod("POST");
		// urlConnection.setUseCaches(false);
		urlConnection.setDoInput(true);// 表示从服务器获取数据
		urlConnection.setDoOutput(true);// 表示向服务器写数据
		// 获得上传信息的字节大小以及长度
		byte[] mydata = buffer.toString().getBytes();
		// 表示设置请求体的类型是文本类型

		urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		urlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
		urlConnection.setRequestProperty("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
		urlConnection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
		urlConnection.setRequestProperty("Origin", "http://jskc.cczu.edu.cn");
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; GT-P5210 Build/JDQ39E) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MicroMessenger/6.3.28.900 NetType/WIFI Language/zh_CN");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// 获得输出流,向服务器输出数据
		OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.write(mydata, 0, mydata.length);
		outputStream.close();
		// 获得服务器响应的结果和状态码
		int responseCode = urlConnection.getResponseCode();

//		Map<String, List<String>> map = urlConnection.getHeaderFields();
//		 System.out.println("\t 所有的headers");

//		String strCookie = urlConnection.getHeaderField("Set-Cookie");
//		 System.out.println("\t strCookie:"+strCookie);
//		String[] value = strCookie.split(";");
//		String[] cookievalue = value[0].split("=");
//		 System.out.println("\t cookievalue:"+cookievalue[0]+"\t"+cookievalue[1]);

		/*
		 * for(Map.Entry entry: map.entrySet()){
		 * System.out.println("\t\t "+entry.getKey()+":"+entry.getValue()); }
		 */
		// System.out.println(responseCode);
		if (responseCode == 200) {
			return changeInputStream(urlConnection.getInputStream(), encode);
		}

		return "";
	}

	/**
	 * 将一个输入流转换成指定编码的字符串
	 * 
	 * @param inputStream
	 * @param encode
	 * @return
	 */
	private static String changeInputStream(InputStream inputStream, String encode) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub Map<String, String> params = new HashMap<String, String>();
	 * params.put("username", "admin"); params.put("password", "123"); String
	 * result = Http_Post.sendPostMessage(params, "utf-8");
	 * System.out.println("--result->>" + result); }
	 */
}
