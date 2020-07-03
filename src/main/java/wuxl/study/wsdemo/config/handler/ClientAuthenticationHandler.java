package wuxl.study.wsdemo.config.handler;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.jdom.Element;


/**
 * @program: wsclientdemo2
 * @author: 吴小龙
 * @create: 2020-06-08 12:06
 * @description: 身份认证对象
 */

public class ClientAuthenticationHandler extends AbstractHandler {
    private String username = null;
    private String password = null;

    public ClientAuthenticationHandler() {
    }

    public ClientAuthenticationHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void invoke(MessageContext messageContext) throws Exception {
        Element el = new Element("header");
        messageContext.getOutMessage().setHeader(el);
        Element auth = new Element("AuthenticationToken");
        Element username_el = new Element("Username");
        username_el.addContent(username);
        auth.addContent(username_el);
        Element password_el = new Element("Password");
        password_el.addContent(password);
        auth.addContent(password_el);
        el.addContent(auth);
    }
}
