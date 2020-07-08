package wuxl.study.wsdemo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;


/**
 * @program: webservice
 * @author: 吴小龙
 * @create: 2020-07-07 16:53
 * @description:
 */

public class JsonUtils  {
    private static final String ENCODING = "UTF-8";

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     */
    public static String jsonToPrettyXml(JSONObject json , String character)  {
        try {
            Document document = jsonToDocument(json,character);
            /* 格式化xml */
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置缩进为4个空格
            format.setEncoding(character);
            StringWriter formatXml = new StringWriter();
            XMLWriter writer = new XMLWriter(formatXml, format);
            writer.write(document);
            return document.asXML();

        } catch (SAXException e) {

        }catch (IOException e){

        }
        return null;
    }



    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws SAXException
     */
    private static Document jsonToDocument(JSONObject json, String character) throws SAXException {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(character);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Object obj =  json.get(rootKey);
            Element root = null;
            if (obj instanceof JSONObject) {
                root = jsonToElement( (JSONObject)obj , rootKey);
                document.add(root);
            }else if (obj instanceof JSONArray) {
                JSONArray jo = (JSONArray)obj;
                for (int i = 0; i< jo.size();i++ ) {
                    root = jsonToElement( (JSONObject)jo.get(i) , rootKey);
                    document.add(root);
                }

            }
            break;
        }
        return document;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json JSON对象
     * @param nodeName 节点名称
     * @return Element对象
     */
    private static Element jsonToElement(JSONObject json, String nodeName) {
        Element node = DocumentHelper.createElement(nodeName);
        for (String key : json.keySet()) {
            try {
                Object child = json.get(key);
                if (child instanceof JSONObject) {
                    node.add(jsonToElement(json.getJSONObject(key), key));
                } else if (child instanceof JSONArray) {
                    JSONArray jo = (JSONArray) child;
                    for (int i = 0; i < jo.size(); i++) {
                        Object co =  jo.get(i);
                        if(co instanceof JSONObject){
                            node.add(jsonToElement((JSONObject)co, key));
                        }else{
                            Element element = DocumentHelper.createElement(key);
                            element.setText(co.toString());
                            node.add(element);
                        }
                    }
                } else {
                    if(key.equals("-NAME")){
                        node.addAttribute("NAME", json.getString(key));
                    }else if(key.equals("#text")){
                        node.addText(json.getString(key));
                    }else{
                        System.out.println("失败");
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return node;
    }


    public static void main(String[] args) {
        String jsonstring="{\"DBSET\":{\"ROW\":[{\"COL\":[{\"-NAME\":\"RTN_FLAG\",\"#text\":\"1\"},{\"-NAME\":\"RTN_MEMO\",\"#text\":\"success\"},{\"DBSET\":[{\"ROW\":[{\"COL\":[{\"-NAME\":\"RTN_FLAG3\",\"#text\":\"3\"},{\"-NAME\":\"RTN_MEMO3\",\"#text\":\"dontknow\"}]}]}]}]}]}}";
        JSONObject jsonObject= (JSONObject) JSONObject.parse(jsonstring);
        String xml = jsonToPrettyXml(jsonObject, ENCODING);
        System.out.println(xml);
    }

}
