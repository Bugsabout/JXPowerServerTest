package wuxl.study.wsdemo.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wuxl.study.webservice.jxupload.model.BATCH_NO_Model;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-09 13:37
 * @description: DOM4J实现,将Java实体转换为对应的xml格式字符串。注意！！！参数必须是Bean，如果用json格式会报错
 */
//！！！如果object存在实例对象，但所有的属性都不存在值，则会生成错误的xml字符串
public class JavaToXmlUtil {


    public static String JavaToXML(Object object) throws IOException {




        Document document = DocumentHelper.createDocument();
        Element dbset = document.addElement("DBSET");

        //创建子节点ROW
        Element row1 = dbset.addElement("ROW");

        //查看object的属性数量
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            //遍历属性列表，将有值的参数，放到ROW节点下
            Object fieldValueByName = getFieldValueByName(fields[i].getName(), object);
            if(fieldValueByName!=null){
                Element element = row1.addElement("COL");
                element.addAttribute("NAME",fields[i].getName());
                element.addText(fieldValueByName.toString());
            }
        }

        document.setRootElement(dbset);
        return document.asXML();
    }



    private static Object getFieldValueByName(String fieldName, Object o) {
        try{
            String firstLetter=fieldName.substring(0,1).toUpperCase();
            String getter="get"+firstLetter+fieldName.substring(1);
            Method method=o.getClass().getMethod(getter, new Class[]{});   //反射机制
            Object value=method.invoke(o, new Object[]{});
            return value;
        }catch (Exception e){
            System.out.println("属性值不存在");
            return null;
        }
    }

    public static void main(String[] args) {
        BATCH_NO_Model batch_no_model = new BATCH_NO_Model();
        batch_no_model.setBATCH_NO("2016061511350912");
        try {
            String s = JavaToXML(batch_no_model);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
