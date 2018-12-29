package mian.cn.tong.wx;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String code =req.getParameter("code");
        String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code ";
        authUrl= authUrl.replace("APPID","wxbcfe96ce8d0b891c");
        authUrl = authUrl.replace("SECRET", "916c617bd79602c8dd25d0e482cb3467");
        authUrl = authUrl.replace("CODE", code);
        resp.sendRedirect(authUrl);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
