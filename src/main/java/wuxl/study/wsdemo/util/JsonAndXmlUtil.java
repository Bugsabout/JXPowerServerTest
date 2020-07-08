package wuxl.study.wsdemo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * @program: webservice
 * @author: 吴小龙
 * @create: 2020-07-07 15:24
 * @description: JX项目专用xml和json数据格式转化工具类
 */

public class JsonAndXmlUtil {

    private static final String ENCODING = "UTF-8";

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     */
    public static String jsonToPrettyXml(JSONObject json , String character)  {
        try {
            org.dom4j.Document document = jsonToDocument(json,character);
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
    private static org.dom4j.Document jsonToDocument(JSONObject json, String character) throws SAXException {
        org.dom4j.Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(character);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Object obj =  json.get(rootKey);
            org.dom4j.Element root = null;
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
    private static org.dom4j.Element jsonToElement(JSONObject json, String nodeName) {
        org.dom4j.Element node = DocumentHelper.createElement(nodeName);
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
                            org.dom4j.Element element = DocumentHelper.createElement(key);
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



                    // Element element = DocumentHelper.createElement(key);
                    // element.setText(json.getString(key));
                    // node.add(element);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return node;
    }
























    /**
     * 转换一个xml格式的字符串到json格式
     *
     * @param xml xml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    @SuppressWarnings("unchecked")
    public static String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 一个迭代方法
     *
     * @param element : org.jdom.Element
     * @return java.util.Map 实例
     */
    @SuppressWarnings("unchecked")
    private static Map iterateElement(Element element) {
        List jiedian = element.getChildren();
        Element et = null;
        Map obj = new HashMap();
        List list = null;

        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {//判断是否有值
                if (et.getChildren().size() == 0) {
                    continue;
                }
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());//判断是否有这个结点结果了
                }
                Map result = new LinkedHashMap();
                //判断et是否有属性
                List<Attribute> attributes = et.getAttributes();
                for (Attribute attribute : attributes) {
                    result.put("-" + attribute.getName(), attribute.getValue());
                }
                result.put("#text", et.getTextTrim());
                list.add(JSONObject.toJSON(result));
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }



    public static void main(String[] args) {
        String jsonstring = JsonAndXmlUtil.xml2JSON("<DBSET>\n" +
                "<ROW>\n" +
                "    <COL NAME=\"RTN_FLAG\">1</COL>\n" +
                "    <COL NAME=\"RTN_MEMO\">success</COL>\n" +
                "    <COL NAME=\"RTN_MP\">\n" +
                "        <DBSET>\n" +
                "            <ROW>\n" +
                "                <COL NAME=\"RTN_FLAG3\">3</COL>\n" +
                "                <COL NAME=\"RTN_MEMO3\">dontknow</COL>\n" +
                "            </ROW>\n" +
                "        </DBSET>\n" +
                "    </COL>\n" +
                "</ROW>\n" +
                "</DBSET>");

        //xml字符串转为json格式字符串
        System.out.println(jsonstring);

        JSONObject jsonObject= (JSONObject) JSONObject.parse(jsonstring);
        String xml = jsonToPrettyXml(jsonObject, ENCODING);
        System.out.println(xml);

    }
}

