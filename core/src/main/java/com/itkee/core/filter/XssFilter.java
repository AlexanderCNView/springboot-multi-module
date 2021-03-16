package com.itkee.core.filter;

import com.itkee.core.security.XssAndSqlHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rabbit
 */
@Slf4j
public class XssFilter implements Filter {

    private List<String> excludes = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.debug("xss filter is open");
        HttpServletRequest req = (HttpServletRequest) request;
        if(handleExcludeUrl(req)){
            filterChain.doFilter(request, response);
            return;
        }
        String method = "GET";
        String param = "";
        XssAndSqlHttpServletRequestWrapper xssRequest = null;
        if (request != null) {
            method = ((HttpServletRequest) request).getMethod();
            xssRequest = new XssAndSqlHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if ("POST".equalsIgnoreCase(method)) {
            param = getBodyString(xssRequest.getReader());
            if(StringUtils.isNotBlank(param)){
                if(XssAndSqlHttpServletRequestWrapper.checkXSSAndSql(param)){
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.write("您所访问的页面请求中有违反安全规则元素存在，拒绝访问!");
                    return;
                }
            }
        }
        if (xssRequest.checkParameter()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write("您所访问的页面请求中有违反安全规则元素存在，拒绝访问!");
            return;
        }
        filterChain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeUrl(HttpServletRequest request) {

        if (excludes == null || excludes.isEmpty()) {
            return false;
        }

        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void init(FilterConfig filterConfig){
        log.debug("xss filter init~~~~~~~~~~~~");
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            excludes.addAll(Arrays.asList(url));
        }
    }

    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;

    }
}
