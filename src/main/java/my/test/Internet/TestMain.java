package my.test.Internet;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestMain {
    private static String urlString = "http://jskc.cczu.edu.cn/index.php/Lecture/enrolldo";//报名码(need:userid--学号,lid--讲座号)
    private static String urlQainDao = "http://jskc.cczu.edu.cn/index.php/Lecture/signdo";//签到码(need:userid--学号,vfcode--签到码)

    private String userid;//学号
    private String lid;//讲座号
    private String vfcode;//签到码
    private int temp=0;

    public TestMain() {
    }

    public TestMain(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getVfcode() {
        return vfcode;
    }

    public void setVfcode(String vfcode) {
        this.vfcode = vfcode;
    }


    /**
     * 报名
     *
     * @throws Exception
     */
    public void baoming() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userid", getUserid());
        String result;
        JSONObject json;
        map.put("lid", getLid());
        int count = 0;

        while (true) {
            System.out.println("学号:" + getUserid() + "   次数:" + count++ + ".........");
            result = Http_Post.sendPostMessage(urlString, map, "");
            json = JSONObject.parseObject(result);
            if (Boolean.valueOf((String) json.get("state"))) {
                System.out.println(json.get("message"));
                break;
            }
            System.out.println("\t" + json.get("state") + "\t" + json.get("message"));
            if (count % 100 == 0)
                Thread.sleep(1000);
        }
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    /**
     * 签到
     *

     * @throws Exception
     */
    public void qiandao() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userid", getUserid());
        String result;
        JSONObject json;
        if (null == getVfcode() || getVfcode().equals("")) {//如果不知道签到码,则循环获取
            for (; temp < 10000; temp++) {
                System.out.println("当前学号: "+getUserid());
                map.put("vfcode", "16" + String.format("%04d", temp));
                result = Http_Post.sendPostMessage(urlQainDao, map, "");
                json = JSONObject.parseObject(result);
                if (Boolean.valueOf((String) json.get("state"))) {

                    System.out.println(json.get("message")+" 签到码为: "+map.get("vfcode"));
                    break;
                }
                System.out.println("签到码"+map.get("vfcode")+" \t" + json.get("state") + "\t" + json.get("message"));
                if (temp % 100 == 0)
                    Thread.sleep(1000);
            }
        } else {
            map.put("vfcode", getVfcode());
            result = Http_Post.sendPostMessage(urlQainDao, map, "");
            json = JSONObject.parseObject(result);
            System.out.println("当前学号: "+getUserid());
            System.out.println("\t" + "已获取签到码:"+getVfcode()+"  " + json.get("state") + "\t" + json.get("message"));
        }

    }

}
