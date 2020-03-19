package com.itkee.core.logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author rabbit
 */
@Component
@Aspect
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution( * com.itkee.*.controller.*.*(..))")
    public void privilege() {}


    @AfterThrowing(pointcut = "privilege()", throwing = "e")
    public void afterThrowing(Throwable e) {
        log.info(e.getMessage());
    }
    /**
     * 环绕方法
     * @param pjp pjp
     * @return object
     * @throws Throwable Throwable
     */
    @Around("privilege()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取类名
        String className = pjp.getTarget().getClass().getName();
        // 获取执行的方法名称
        String methodName = pjp.getSignature().getName();
        // 获取参数名称
        String[] parameterNamesArgs = ((MethodSignature) pjp.getSignature()).getParameterNames();
        // 定义返回参数
        Object result;
        // 获取方法参数
        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 请求的URL
        String requestUrl = request.getRequestURL().toString();
        String ip = getIpAddr(request);

        StringBuilder paramsBuf = new StringBuilder();
        // 获取请求参数集合并进行遍历拼接
        for (int i = 0; i < args.length; i++) {
            if (paramsBuf.length() > 0) {
                paramsBuf.append("|");
            }
            paramsBuf.append(parameterNamesArgs[i]).append(" = ").append(args[i]);
        }
        StringBuilder headerBuf = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            if (headerBuf.length() > 0) {
                headerBuf.append("|");
            }
            headerBuf.append(key).append("=").append(value);
        }
        // 打印请求参数参数
        long start = System.currentTimeMillis();
        log.info("请求| ip:{} | 请求接口:{} | 请求类:{} | 方法 :{} | 参数:{} | 请求header:{}|请求时间 :{}", ip, requestUrl, className, methodName, paramsBuf.toString(), headerBuf.toString(), start);
        result = pjp.proceed();
        log.info("返回| 请求接口:{}| 方法 :{} | 请求时间:{} | 处理时间:{} 毫秒 | 返回结果 :{}", requestUrl, methodName, start, (System.currentTimeMillis() - start), result);
        return result;
    }

    /**
     * @Description: 获取ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        String unknown = "unknown";
        String localIp = "127.0.0.1";
        int len = 15;
        String split = ",";
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (localIp.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if(inet != null){
                        ipAddress = inet.getHostAddress();
                    }else{
                        ipAddress = "";
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && len < ipAddress.length()) {
                // = 15
                if (ipAddress.indexOf(split) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }
}
