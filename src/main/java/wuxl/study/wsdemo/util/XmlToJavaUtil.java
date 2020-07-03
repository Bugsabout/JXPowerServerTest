package wuxl.study.wsdemo.util;

import com.alibaba.fastjson.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-09 15:43
 * @description: 将xml获取的xml内容转化为Java格式
 */


//todo  这个转换为json还需要修改一下, 目前的转换时只能转化所有的,会将DBSET和ROW一同转化,我实际上只需要ROL中以name为key的json对象
public class XmlToJavaUtil {
    public static String xml2JSONWithout_DBSET_ROW_COL(String xml) {
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Map iterateElement(Element element) {
        List jiedian = element.getChildren();
        Element et = null;
        Map obj = new HashMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
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
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                if(et.getAttribute("NAME")!=null){
                    System.out.println(et.getAttribute("NAME").getValue());
                    obj.put(et.getAttribute("NAME").getValue(), list);
                }else{
                    obj.put(et.getName(), list);

                }


            }
        }
        return obj;
    }


}
