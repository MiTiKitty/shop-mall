package top.itcat.mall.security.component;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.common.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @className: RateLimitInterceptor <br/>
 * @description: 速率限制拦截器 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/05/27 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        String path = request.getServletPath();
        String method = request.getMethod();
        String ip = RequestUtil.getRequestIp(request);
        String preKey = "itcat:mall:limit:" + method + ":" + path + ":" + ip + ":";
        long currentTime = System.currentTimeMillis() / 1500L;
        String timeKey = preKey + currentTime;
        Long limitTime = 1L;
        Long increment = redisService.incr(timeKey, 1L);
        redisService.expire(timeKey, limitTime, TimeUnit.SECONDS);
        if (increment > 5L) {
            log.debug("当前访问 -> path:" + path + ", method:" + method + ", ip:" + ip + ", 限定时间(" + currentTime + ")s 访问次数:" + increment + ", 访问次数超出限制");
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return false;
        }
        log.info("当前访问 -> path:" + path + ", method:" + method + ", ip:" + ip + ", 限定时间(" + currentTime + ")s 访问次数:" + increment);
        return true;
    }
}
