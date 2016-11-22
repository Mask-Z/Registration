package my.test.Internet;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestMain {
	private static String urlString = "http://jskc.cczu.edu.cn/index.php/Lecture/enrolldo";//报名码(need:userid--学号,lid--讲座号)
	private static String urlQainDao = "http://jskc.cczu.edu.cn/index.php/Lecture/signdo";//签到码(need:userid--学号,vfcode--签到码)

	public static void main(String[] args) throws Exception {
		baoming();
		qiandao();

	}

	public static void baoming() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", "13477129");
		String result;
		JSONObject json;
		for (int i = 39; i <= 39; i++) {
			map.put("lid", "" + i);
			result = Http_Post.sendPostMessage(urlString, map, "");
			json = JSONObject.parseObject(result);
			if (Boolean.valueOf((String) json.get("state"))) {
				System.out.println(json.get("message"));
			}
			System.out.println("\t" + json.get("state") + "\t" + json.get("message"));


		}
	}

	public static void qiandao() throws Exception {//签到
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", "13477129");
		String result;
		JSONObject json;
		for (int i = 0; i < 10000; i++) {
			map.put("vfcode", "16" + String.format("%04d", i));
			result = Http_Post.sendPostMessage(urlQainDao, map, "");
			json = JSONObject.parseObject(result);
			if (Boolean.valueOf((String) json.get("state"))) {
				System.out.println(json.get("message"));
				break;
			}
			System.out.println("\t" + json.get("state") + "\t" + json.get("message"));
		}
	}




}
