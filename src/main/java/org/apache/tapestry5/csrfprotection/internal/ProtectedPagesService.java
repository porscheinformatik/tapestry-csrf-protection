package org.apache.tapestry5.csrfprotection.internal;

import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.csrfprotection.CsrfProtectionMode;
import org.apache.tapestry5.csrfprotection.NotCsrfProtected;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentEventRequestParameters;

/**
 * Service that provides information which pages are protected.
 */
public class ProtectedPagesService
{
    private final ComponentClassResolver componentClassResolver;
    private final CsrfProtectionMode antiCsrfMode;

    /**
     * @param componentClassResolver injected
     * @param antiCsrfMode symbol {@link CsrfConstants#CSRF_PROTECTION_MODE}
     */
    public ProtectedPagesService(ComponentClassResolver componentClassResolver,
        @Value("${" + CsrfConstants.CSRF_PROTECTION_MODE + "}") CsrfProtectionMode antiCsrfMode)
    {
        this.componentClassResolver = componentClassResolver;
        this.antiCsrfMode = antiCsrfMode;
    }

    /**
     * @param parameters the {@link ComponentEventRequestParameters} for the request or link
     * @return true if page should be CSRF protected, false otherwise
     */
    public boolean isPageProtected(ComponentEventRequestParameters parameters)
    {
        if (antiCsrfMode == CsrfProtectionMode.OFF)
        {
            return false;
        }

        Class<?> pageClass = getPageClass(parameters);
        return !pageClass.isAnnotationPresent(NotCsrfProtected.class);
    }

    private Class<?> getPageClass(ComponentEventRequestParameters parameters)
    {
        String pageClassName = componentClassResolver.resolvePageNameToClassName(parameters.getActivePageName());
        Class<?> pageClass;
        try
        {
            pageClass = Class.forName(pageClassName);
        }
        catch (ClassNotFoundException e)
        {
            throw new TapestryException("Page with class name " + pageClassName + " not found", e);
        }
        return pageClass;
    }
}
