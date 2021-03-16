package at.porscheinformatik.tapestry.csrfprotection.internal;

import org.apache.tapestry5.commons.ObjectLocator;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * Util class for getting Spring beans.
 */
public final class SpringContextHelper
{
    private SpringContextHelper()
    {
    }

    /**
     * Looks up Spring's {@link ApplicationContext} and gets bean. Is safe even if Spring, Tapestry Spring integration
     * or the bean is not on classpath.
     * 
     * @param objectLocator the {@link ObjectLocator}
     * @param type the desired bean type
     * @return the Spring bean or null if no Spring on classpath, or no Spring Tapestry integration or bean class not on
     *         classpath
     */
    public static Object getSpringBean(ObjectLocator objectLocator, String type)
    {
        Class<?> springAppContextClass;
        Class<?> beanTypeClass;
        try
        {
            springAppContextClass = Class.forName("org.springframework.context.ApplicationContext");
            beanTypeClass = Class.forName(type);
        }
        catch (ClassNotFoundException e)
        {
            return null;
        }

        // org.springframework.context.ApplicationContext
        Object applicationContext;
        try
        {
            applicationContext = objectLocator.getService(springAppContextClass);
        }
        catch (RuntimeException e)
        {
            return null;
        }

        try
        {
            Method getBean = applicationContext.getClass().getMethod("getBean", Class.class);
            return getBean.invoke(applicationContext, beanTypeClass);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
