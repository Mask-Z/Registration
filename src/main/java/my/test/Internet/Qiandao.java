package my.test.Internet;

import java.util.List;

import static my.test.Internet.ShuaKa.getList;

/**
 * Created by zyl on 2016/11/23.
 */
public class Qiandao {
    public static void main(String[] args) throws Exception {
        List<TestMain> mainList = getList();
        for(int i=0;i<mainList.size();i++){
            mainList.get(i).qiandao();
        }
    }
}
