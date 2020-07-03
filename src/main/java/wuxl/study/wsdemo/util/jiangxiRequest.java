package wuxl.study.wsdemo.util;

import org.codehaus.xfire.client.Client;
import wuxl.study.wsdemo.config.handler.ClientAuthenticationHandler;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-09 16:41
 * @description: 江西接口请求路径，传递xml并返回对应值
 */

public class jiangxiRequest {


    public static Object[] getRequestResult(String URL, String xmlParams,String serviceName) {
        //方法封装,通过url和xml文件的字符串格式请求接口获取对应结果

        try {
            if(URL==null){
                throw new Exception("URL不能为空");
            }
             java.net.URL _url = new URL(URL);
            Object[] obj = {xmlParams};
            HttpURLConnection httpConnection = (HttpURLConnection) _url.openConnection();
            httpConnection.setReadTimeout(20000);
            httpConnection.connect();
            Client _client = new Client(httpConnection.getInputStream(), null);
            //todo 照理说账号密码是不能显示,通过传递参数,添加header认证
            _client.addOutHandler(new ClientAuthenticationHandler("abcd", "1234")); //设置身份证信息(账号、密码)
            Object[] result = _client.invoke(serviceName, obj);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    ;
}
