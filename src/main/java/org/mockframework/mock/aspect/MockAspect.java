package org.mockframework.mock.aspect;

import org.mockframework.mock.annotation.MockRequest;
import org.mockframework.mock.api.Storable;
import org.mockframework.mock.config.AspectConfigurer;
import org.mockframework.mock.store.FileStore;
import org.mockframework.mock.util.SignatureUtil;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * Created by deepWhite on 17/5/27.
 */
@Setter
public class MockAspect {
    @Autowired
    AspectConfigurer aspectConfigurer;

    private static Storable store = new FileStore();

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class<?> cl = Class.forName(className);

        boolean mockFlag = false;
        MockRequest mockClass = cl.getAnnotation(MockRequest.class);
        String mockSwitch = aspectConfigurer.getMockSwitch();
        if (mockClass != null && mockSwitch.equals("true")) {
            mockFlag = true;
        }
        Object[] args = joinPoint.getArgs();
        StringBuffer argStr = new StringBuffer();
        Class<?>[] classArgs = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classArgs[i] = args[i].getClass();
            argStr.append(args[i]);
        }
        if (!mockFlag) {
            Method method = cl.getMethod(methodName, classArgs);
            System.out.println(method.toString());
            MockRequest mockMethod = method.getAnnotation(MockRequest.class);
            if (mockMethod != null &&  mockSwitch.equals("true")) {
                mockFlag = true;
            }
        }
        System.out.println(className + methodName + "|" + argStr.toString());

        String feature = SignatureUtil.getMD5(className + methodName +  argStr.toString());
        Object resultData;
        if (mockFlag) {
            resultData = store.consumerMockData(feature, joinPoint);
        } else {
            resultData = joinPoint.proceed();
            store.productMockData(feature, resultData, joinPoint);
        }

        return resultData;
    }
}
