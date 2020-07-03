package wuxl.study.wsdemo.serviceImpl;

import org.springframework.stereotype.Service;
import wuxl.study.wsdemo.entity.User;
import wuxl.study.wsdemo.service.IUserService;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-15 21:51
 * @description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public boolean checkUser(String loginName, String passWord) {
        return true;
    }

    @Override
    public User getUser(String loginName) {
        User user = new User();
        user.setUsername("李四");
        user.setPassword("123");
        return user;
    }
}
