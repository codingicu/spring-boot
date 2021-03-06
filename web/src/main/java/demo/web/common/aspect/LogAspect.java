package demo.web.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 项目: spring-boot
 * 时间: 2021/11/12 17:16
 *
 * @author HuangYu
 * @version 1.0.0
 * @since JDK1.8
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    private final ObjectMapper objectMapper;

    public LogAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Before("execution(public * demo.web.controller.*.*(..))")
    public void before() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes为空");
        HttpServletRequest request = requestAttributes.getRequest();

        String host = request.getRemoteHost();
        String url = request.getRequestURI();
        String method = request.getMethod();
        String args = null;
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            args = objectMapper.writeValueAsString(parameterMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String session = request.getSession().getId();
        log.info("host:{}\turl:{}\tmethod:{}\targs:{}\tsession:{}", host, url, method, args, session);
    }
}
