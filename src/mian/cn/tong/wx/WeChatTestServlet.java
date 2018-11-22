package mian.cn.tong.wx;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class WeChatTestServlet extends HttpServlet {
    /*
     * 自定义token, 用作生成签名,从而验证安全性
     * */
    private final String TOKEN = "tong";



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     System.out.println("开始校验签名");

        // 接收微信服务器发送请求时传递过来的参数
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce"); //随机数
        String echostr = req.getParameter("echostr");//随机字符串

        String sortStr = sort(TOKEN,timestamp,nonce);
        /**
         * 字符串进行shal加密
         */
        String mySignature = shal(sortStr);

        if(!"".equals(signature) && !"".equals(mySignature)&&signature.equals(mySignature)){

            System.out.println("签名通过");
            resp.getWriter().write(echostr);
        }else{
            System.out.print("校验失败");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 接收、处理、响应由微信服务器转发的用户发送给公众帐号的消息
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("请求进入");
        String result = "";
        try {
            Map<String,String> map = MessageUtil.parseXml(req);

            System.out.println("开始构造消息");
            String msgType = map.get("MsgType").toString();
            System.out.println("MsgType:" + msgType);

            if (msgType.toUpperCase().equals("EVENT")) {
                result = MessageUtil.buildXml(map);
            } else if(msgType.toUpperCase().equals("TEXT")) {
                result = MessageUtil.buildTextMessage(map,"王宁是shit,陈伟是shit");
            }else if(msgType.toUpperCase().equals("IMAGE")){
                result = MessageUtil.buildImageMessage(map,"");
            }else if(msgType.toUpperCase().equals("VOICE")){
                result = MessageUtil.buildVoiceMessage(map);
            }else if(msgType.toUpperCase().equals("VIDEO")){
                result = MessageUtil.buildVideoMessage(map);
            }
            System.out.println(result);

            if(result.equals("")){
                result = "未正确响应";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发生异常："+ e.getMessage());
        }
        resp.getWriter().println(result);

    }


    /**
     * 参数排序
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 字符串进行shal加密
     * @param str
     * @return
     */
    public String shal(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
