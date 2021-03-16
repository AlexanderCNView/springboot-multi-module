package com.itkee.core.filter;
import com.itkee.core.annotation.PassToken;
import com.itkee.core.annotation.User;
import com.itkee.core.exception.AuthorizationException;
import com.itkee.core.utils.JwtHelper;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * JWT过滤器
 * @author rabbit
 */
public class JwtFilter implements HandlerInterceptor {

    private final JwtHelper jwtHelper;
    private final List<String> urls;
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public JwtFilter(JwtHelper jwtHelper, String[] authorisedUrls) {
        this.jwtHelper = jwtHelper;
        urls = Arrays.asList(authorisedUrls);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String path = request.getServletPath();
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        for (String url : urls) {
            if (PATH_MATCHER.match(url, path)) {
                Optional<User> user = Optional.ofNullable(jwtHelper.validateTokenAndGetClaims(request));
                if (user.isPresent()) {
                    User currentUser = (User) request.getAttribute("currentUser");
                    if(currentUser == null){
                        request.setAttribute("currentUser",user.get());
                    }
                    return true;
                }else{
                    throw new AuthorizationException("未授权或者授权已经过期");
                }
            }else{
                return true;
            }
        }
        return true;
    }
}
