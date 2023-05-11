package by.sunshine.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @After("by.sunshine.aop.Pointcuts.beforeMainPageMethod()")
    private void getBookCheck() {
        log.info("User go to main page!");
    }

}
