package mian.cn.tong.wx;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class UploadMediaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil();
         String appId="wxbcfe96ce8d0b891c";
         String appsecret="916c617bd79602c8dd25d0e482cb3467";

        String accessToken = AccessToken.tokenName;

        String filePath = "C:\\Users\\Administrator\\Desktop\\7.jpg";
        File file = new File(filePath);
        String type = "IMAGE";
        JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type);
        System.out.println(jsonObject.toString());

    }
}
