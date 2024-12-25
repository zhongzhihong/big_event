package com.zzh.common;

import com.zzh.utils.JwtUtil;
import com.zzh.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证token
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存储进ThreadLocal中
            // 由于token变量会在多个接口中使用到，为了让代码可读性更高，可以使用ThreadLocal来优化
            // 可以维护一个全局ThreadLocal对象来存储token，那么请求到达拦截器之后，调用该对象的set()方法存储一个token
            // 当请求到达其他接口时，就可以调用该对象的get()方法获取该token
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空ThreadLocal中的数据，避免内存泄漏
        // 当请求完成后，就可以对ThreadLocal对象进行清空操作，所以在afterCompletion()中执行即可
        ThreadLocalUtil.remove();
    }
}
