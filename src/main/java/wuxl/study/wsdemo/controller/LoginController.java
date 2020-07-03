package wuxl.study.wsdemo.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wuxl.study.wsdemo.entity.User;
import wuxl.study.wsdemo.service.IUserService;
import wuxl.study.wsdemo.util.AjaxResultUtil;
import wuxl.study.wsdemo.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-15 21:48
 * @description:
 */
@Controller
@RequestMapping
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;


    @PostMapping("login")
    @ResponseBody
    public AjaxResultUtil login (@RequestBody Map<String,String> map){
        String loginName = map.get("loginName");
        String passWord = map.get("passWord");
        //身份验证
        boolean isSuccess =  userService.checkUser(loginName,passWord);
        if (isSuccess) {
            //模拟数据库查询
            User user = userService.getUser(loginName);
            if (user != null) {
                //返回token
                String token = JwtUtil.sign(loginName, passWord);
                if (token != null) {
                    return AjaxResultUtil.success("成功",token);
                }
            }
        }
        return AjaxResultUtil.fail();
    }

    @PostMapping("getUser")
    @ResponseBody
    public AjaxResultUtil getUserInfo(HttpServletRequest request, @RequestBody Map<String, String> map){
        String loginName = map.get("loginName");
        String token = request.getHeader("token");
        boolean verity = JwtUtil.verity(token);
        if (verity) {
            User user = userService.getUser(loginName);
            if (user != null) {
                return AjaxResultUtil.success("成功", JSONObject.toJSONString(user));
            }
        }
        return AjaxResultUtil.fail();
    }

}
