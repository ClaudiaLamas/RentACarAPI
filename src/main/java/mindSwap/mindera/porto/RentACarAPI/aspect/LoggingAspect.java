package mindSwap.mindera.porto.RentACarAPI.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* SpringBootCarRental.CarRentalSpringBoot.service.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in " + joinPoint.getSignature().getName() + " method call");
        logger.error("Exception: " + exception);
    }



}
