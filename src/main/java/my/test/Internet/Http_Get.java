package my.test.Internet;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Http_Get {

	public Http_Get() {
		// TODO Auto-generated constructor stub
	}

	public static void saveImageToDisk(String url) {
		InputStream inputStream = getInputStream(url);
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("C:\\test.png");
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得服务器端的数据,以InputStream形式返回
	 * 
	 * @return
	 */
	public static InputStream getInputStream(String strUrl) {

		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(strUrl);
			if (url != null) {
				httpURLConnection = (HttpURLConnection) url.openConnection();
				// 设置连接网络的超时时间
				httpURLConnection.setConnectTimeout(3000);
				httpURLConnection.setDoInput(true);
				// 表示设置本次http请求使用GET方式请求
				httpURLConnection.setRequestMethod("GET");
				if (httpURLConnection.getResponseCode() == 200) {
					InputStream is = httpURLConnection.getInputStream();
					return is;
				} else {
					System.out.println("[error] 获取失败，网页错误码:" + httpURLConnection.getResponseCode());
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getContentFromUrl(String strUrl, String encode) throws Exception {
		String result = null;
		if (encode != null && !encode.equals("")) {
		} else {
			encode = "utf-8";
		}
		HttpURLConnection httpURLConnection = null;

		URL url = new URL(strUrl);
		if (url != null) {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置连接网络的超时时间
			httpURLConnection.setConnectTimeout(3000);
			httpURLConnection.setDoInput(true);
			// 表示设置本次http请求使用GET方式请求
			httpURLConnection.setRequestMethod("GET");
			// int responseCode = httpURLConnection.getResponseCode();
			if (httpURLConnection.getResponseCode() == 200) {
				InputStream is = httpURLConnection.getInputStream();
				result = changeInputStream(is, encode);

			} else {
				System.out.println("[error] 获取失败，网页错误码:" + httpURLConnection.getResponseCode());
			}
		}

		return result;
	}

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
}
