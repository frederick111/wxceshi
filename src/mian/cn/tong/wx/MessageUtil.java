package mian.cn.tong.wx;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    /**
     * 解析微信发来的请求（XML）
     * @param request
     * @return map
     * @throws Exception
     */
    public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String,String> map = new HashMap();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        System.out.println("获取输入流");
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }
        System.out.println(map.get("MediaId"));
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 根据消息类型 构造返回消息
     */
    public static String buildXml(Map<String,String> map) {
        String result;


            String fromUserName = map.get("FromUserName");
            // 开发者微信号
            String toUserName = map.get("ToUserName");
            result = String
                    .format(
                            "<xml>" +
                                    "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                    "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                    "<CreateTime>%s</CreateTime>" +
                                    "<MsgType><![CDATA[text]]></MsgType>" +
                                    "<Content><![CDATA[%s]]></Content>" +
                                    "</xml>",
                            fromUserName, toUserName, getUtcTime(),
                            "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");


        return result;
    }
    /**
     * 构造文本消息
     *
     * @param map
     * @param content
     * @return
     */
    public static String buildTextMessage(Map<String,String> map, String content) {
        //发送方帐号
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        /**
         * 文本消息XML数据格式
         */
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" + "</xml>",
                fromUserName, toUserName, getUtcTime(), content);
    }

    public static String getUtcTime() {
        Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
        String nowTime = df.format(dt);
        long dd = (long) 0;
        try {
            dd = df.parse(nowTime).getTime();
        } catch (Exception e) {

        }
        return String.valueOf(dd);
    }


    /**
     *  构建图片消息
     * @param map
     * @param picUrl
     * @return
     */
    public static String buildImageMessage(Map<String, String> map, String picUrl) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        /*返回指定的图片(该图片是上传为素材的,获得其media_id)*/
       // String media_id = "ZJStSrdoo9nxQ1775tsvcIS_BNp6Xp_WxTTxYWEUqgLqFYmWfdl-hGCVjOVFPAmK";

        /*返回用户发过来的图片*/
        String media_id = map.get("MediaId");
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[image]]></MsgType>" +
                        "<Image>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Image>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id
        );
    }

    /**
     * 构造语音消息
     * @param map
     * @return
     */
    public static String buildVoiceMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        /*返回用户发过来的语音*/
       // String media_id = map.get("MediaId");
        String media_id="_wdr8zkUxdaJxMi_hK3wa783-6id0o587FQ3UrWLTzUSZvN1xNm8b4UhXHrSdjby";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[voice]]></MsgType>" +
                        "<Voice>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "</Voice>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id
        );
    }


    /**
     * 回复视频消息
     * @param map
     * @return
     */
    public static String buildVideoMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String title = "客官发过来的视频哟~~";
        String description = "客官您呐,现在肯定很开心,对不啦 嘻嘻��";
        /*返回用户发过来的视频*/
        //String media_id = map.get("MediaId");
        String media_id = "hTl1of-w78xO-0cPnF_Wax1QrTwhnFpG1WBkAWEYRr9Hfwxw8DYKPYFX-22hAwSs";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[video]]></MsgType>" +
                        "<Video>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "   <Title><![CDATA[%s]]></Title>" +
                        "   <Description><![CDATA[%s]]></Description>" +
                        "</Video>" +
                        "</xml>",
                fromUserName,toUserName, getUtcTime(),media_id,title,description
        );
    }


}
