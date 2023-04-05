package org.example.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: zjh
 * @Date: 2023/4/5 08:49
 */
@Component
public class SpringBootBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("hittttt");
        if (null == SpringBootBeanUtils.applicationContext) {
            SpringBootBeanUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static boolean hasBean(Class<?> clazz) {
        try {
            applicationContext.getBean(clazz);
            return true;
        }catch (NoSuchBeanDefinitionException exception) {
            return false;
        }
    }
}
