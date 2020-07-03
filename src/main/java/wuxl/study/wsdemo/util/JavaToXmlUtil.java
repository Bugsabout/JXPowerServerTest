package wuxl.study.wsdemo.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-09 13:37
 * @description: DOM4J实现,将Java实体转换为对应的xml格式字符串
 */

public class JavaToXmlUtil {
    public static String JavaToXML(Object object) throws IOException {



        // 输出格式
        // OutputFormat format = OutputFormat.createPrettyPrint();
        // format.setEncoding("utf-8");// 设置XML文件的编码格式
        // format.setLineSeparator("\n");
        // format.setTrimText(false);
        // format.setIndent("	");


        Document document = DocumentHelper.createDocument();
        Element dbset = document.addElement("DBSET");

        //创建子节点ROW
        Element row = dbset.addElement("ROW");

        //查看object的属性数量
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            //遍历属性列表，将有值的参数，放到ROW节点下
            Object fieldValueByName = getFieldValueByName(fields[i].getName(), object);
            if(fieldValueByName!=null){
                Element element = row.addElement("COL");
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
            Method method=o.getClass().getMethod(getter, new Class[]{});
            Object value=method.invoke(o, new Object[]{});
            return value;
        }catch (Exception e){
            System.out.println("属性值不存在");
            return null;
        }
    }
}
