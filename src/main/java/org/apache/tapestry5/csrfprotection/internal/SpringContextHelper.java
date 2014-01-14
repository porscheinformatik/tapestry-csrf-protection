package org.apache.tapestry5.csrfprotection.internal;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.springframework.context.ApplicationContext;

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

        ApplicationContext applicationContext;
        try
        {
            applicationContext = (ApplicationContext) objectLocator.getService(springAppContextClass);
        }
        catch (RuntimeException e)
        {
            return null;
        }

        return applicationContext.getBean(beanTypeClass);
    }
}
