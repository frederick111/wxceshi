package mian.cn.tong.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AccessTokenServlet extends HttpServlet {

    private final String appId="wxbcfe96ce8d0b891c";
    private final String appsecret="916c617bd79602c8dd25d0e482cb3467";

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("-----启动AccessTokenServlet-----");
        super.init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                    AccessToken token=getAccessToken(appId,appsecret);
                    System.out.println(token)   ;
                    if (token!=null){
                            Thread.sleep(7000*1000);
                    }else{
                        System.out.println("获取失败");
                    }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    private AccessToken getAccessToken(String appId, String appSecret) {
        NetWorkUtil netHelper = new NetWorkUtil();
        /**
         * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，其中grant_type固定写为client_credential即可。
         */
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
        //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
        String result = netHelper.getHttpsResponse(Url, "");
        System.out.println("获取到的access_token="+result);

        //使用FastJson将Json字符串解析成Json对象
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setTokenName(json.getString("access_token"));
        token.setExpireSecond(json.getInteger("expires_in"));
        return token;
    }


}
