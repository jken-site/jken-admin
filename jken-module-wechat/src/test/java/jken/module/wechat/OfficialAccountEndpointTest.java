package jken.module.wechat;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.junit.Assert;
import org.junit.Test;

public class OfficialAccountEndpointTest {

    @Test
    public void test() {
        String openid = "obuF-5oOIOwLM2MMppB5WLkzvT_M";
        String signature = "ee73a486ae1347fc22b3d24ad68c36a48f280f80";
        String msgSignature = "6f24636a85eb96d0652d3beb16dcef02ef03be3b";
        String timestamp = "1583639461";
        String nonce = "784467946";
        String requestBody = "<xml>\n" +
                "    <ToUserName><![CDATA[gh_1e689284e16d]]></ToUserName>\n" +
                "    <Encrypt><![CDATA[7ri2cgVJl9efRDkPlWtbDrmPVq1Rz2DEk3j/HhUAKUfuDMKupI+n8OvemBssAvrFcCNKy6UDOjFmbTBf8PPW1Or7L77xDegwatSWUyC2PaF4j2bNTMzTLHAM20iMhqIwqzhOvoBsOvL7dU6aB2aKXLKTlxBv941VaHlL4PPNw4pbYASghG0364jcHcmbjxz7tLHUXw8x3SggEHssec8EvE+tN8J3bZQnd7ySAqdmTw9621qhC7OX2Hok8kWvW3kLXNUbGwNv0eYLeMj4XnM252asJT2bMFEbf3nlScpYVGZhFz6rlk7xBh8pibxaUDpUyn9j5/T0qn5IMiXCCNJJZcn9bxRmDdD7qmEh7qz9e5Vga8cxsLANINI36GzSek0eSEpMSkMzHjSOv5FKxDA/oTMmRgf0qbi8cx8nb/oGh8E=]]></Encrypt>\n" +
                "</xml>";
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId("wx24f97ab0c94540d0");
        config.setSecret("e55a2e9e566235737750b6bd507fa9b3");
        config.setToken("jken");
        config.setAesKey("huSjJYHvwL8HPQdATP1lfemb2LDV7NGOKBt3MzbU1Rx");
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, config, timestamp, nonce, msgSignature);
        Assert.assertTrue("a".equals(inMessage.getContent()));

    }
}
