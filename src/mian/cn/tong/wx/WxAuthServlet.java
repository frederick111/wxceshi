package mian.cn.tong.wx;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WxAuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String takenUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        takenUrl= takenUrl.replace("APPID","wxbcfe96ce8d0b891c");
        takenUrl= takenUrl.replace("REDIRECT_URI", "http://4m52t6.natappfree.cc/authServlet");
        //FIXME ： snsapi_userinfo
        takenUrl= takenUrl.replace("", "snsapi_userinfo");
        System.out.println(takenUrl);
        resp.sendRedirect(takenUrl);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
