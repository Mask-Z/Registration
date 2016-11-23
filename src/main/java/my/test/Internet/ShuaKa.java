package my.test.Internet;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by zyl on 2016/11/14.
 */
public class ShuaKa {
    public static void main(String[] args) {
        List<TestMain> mainList = getList();
        final List<TestMain> finalMainList = mainList;
        int count = mainList.size();
        for (int i = 0; i < count; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        finalMainList.get(finalI).baoming();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static List<TestMain> getList() {

        ResourceBundle bundle = ResourceBundle.getBundle("info");
        String lid = bundle.getString("lid");
        String vfcode = bundle.getString("vfcode");
        String[] userids = bundle.getString("userid").split(",");
        int count = userids.length;
        final List<TestMain> mainList = new ArrayList<TestMain>();
        for (int i = 0; i < count; i++) {//根据学号构造实体类
            TestMain testMain = new TestMain(userids[i].trim());
            testMain.setLid(lid);
            testMain.setVfcode(vfcode);
            mainList.add(testMain);
        }
        return mainList;
    }


}
