package by.sunshine.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(public String mainPage())")
    public void beforeMainPageMethod() {
    }
}
