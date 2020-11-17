package com.cj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.cj.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public  void logBefore(JoinPoint point){
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = point.getSignature().getDeclaringTypeName()+"."+point.getSignature().getName();
        Object args[]=point.getArgs();
        LogRecoder recoder =new LogRecoder( url,ip,classMethod,args);
        logger.info("Visitor :{}",recoder);
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void  logAfterReturning(Object result){
        logger.info("Result: {}",result);
    }

    private class LogRecoder{
        private String url;
        private String ip;
        private  String classMethod;
        private  Object[] args;

        public LogRecoder(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "visitor{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
