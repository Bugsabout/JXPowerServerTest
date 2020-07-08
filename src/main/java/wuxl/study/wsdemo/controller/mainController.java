package wuxl.study.wsdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: wsdemo
 * @author: 吴小龙
 * @create: 2020-06-01 11:31
 * @description: 主业务函数
 */
@RestController
@RequestMapping(value = "/demo")
public class mainController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void showMessage() {
        System.out.println("测试输出调试");
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void showMessage2() {
        System.out.println("测试输出调试w");
    }


    @RequestMapping(value = "/wstest", method = RequestMethod.GET)
    public void wstest() {

    }


}
