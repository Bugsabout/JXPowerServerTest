package wuxl.study.wsdemo;

import org.codehaus.xfire.client.Client;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-08 16:15
 * @description:
 */

public class Json_Xml_Test {
    public static void main(String[] args) throws Exception {
          try {
            String dxpUrl = "http://localhost:8080/services/IMarketDirService?wsdl"; //接口地址
             URL _url = new URL(dxpUrl);
             // String xmlParams="<?xml version=\"1.0\" encoding=\"UTF-8\"?><DBSET><ROW><COL NAME=\"arg0\">0014076002</COL><COL NAME=\"QUERY_PWD\">111111</COL></ROW></DBSET>";
              String xmlParams="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "<DBSET><ROW><COL NAME=\"BATCH_NO\">2016061511350912</COL></ROW></DBSET>";
             Object[] obj = {xmlParams};
            HttpURLConnection httpConnection = (HttpURLConnection) _url.openConnection();
            httpConnection.setReadTimeout(20000);
            httpConnection.connect();
            Client _client = new Client(httpConnection.getInputStream(), null);

            // _client.addOutHandler(new ClientAuthenticationHandler("abcd", "1234")); //设置身份证信息(账号、密码)

            Object[] result = _client.invoke("saleSaveCompantyInfo", obj);
            for (Object o:result){
                System.out.println(o.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // User user = new User();
        // user.setUsername("wuxl");
        // user.setId(1);
        // user.setAge(24);
        // String s = JavaToXmlUtil.JavaToXML(user);
        // System.out.println(s);
        //
        //
        //
        // String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        //         "<DBSET><ROW><COL NAME=\"id\">1</COL><COL NAME=\"name\">wuxl</COL><COL NAME=\"age\">24</COL></ROW></DBSET>";
        // String s1 = XmlToJavaUtil.xml2JSON(xml);
        // JSONObject jsonObject = JSONObject.parseObject(s1);
        // String dbset = jsonObject.getString("DBSET");
        // System.out.println(dbset);
        // JSONObject row = JSONObject.parseObject(dbset);
        // String row1 = row.getString("ROW");
        // System.out.println(row1);


    }
}
