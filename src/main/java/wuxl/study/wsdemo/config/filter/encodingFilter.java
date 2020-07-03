package wuxl.study.wsdemo.config.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-16 16:33
 * @description: 编码拦截器设置
 */
@Configuration
@WebFilter(filterName = "characterEncodingFilter",urlPatterns = "/*")//使用webfilter注解可以生效，在config中配置filter同样也能生效
public class encodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("编码过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");


    }

    @Override
    public void destroy() {
        System.out.println("编码过滤器销毁");

    }
}
