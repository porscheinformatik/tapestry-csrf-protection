package org.apache.tapestry5.csrfprotection.services;

import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.csrfprotection.CsrfProtectionMode;
import org.apache.tapestry5.csrfprotection.internal.CsrfComponentEventLinkTransformer;
import org.apache.tapestry5.csrfprotection.internal.CsrfProtectionFilter;
import org.apache.tapestry5.csrfprotection.internal.CsrfTokenManager;
import org.apache.tapestry5.csrfprotection.internal.ProtectedPagesService;
import org.apache.tapestry5.csrfprotection.internal.SessionCsrfTokenRepository;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ComponentEventRequestFilter;
import org.apache.tapestry5.services.ComponentEventRequestHandler;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;

/**
 * IoC module for Tapestry CSRF Protection. CHECKSTYLE:OFF
 */
public class CsrfProtectionModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(CsrfTokenManager.class);
        binder.bind(ProtectedPagesService.class);
        binder.bind(CsrfTokenRepository.class, SessionCsrfTokenRepository.class);
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("csrf", "org.apache.tapestry5.csrfprotection"));
    }

    @Contribute(ComponentEventLinkTransformer.class)
    public static void addCsrfComponentEventLinkTransformer(
        OrderedConfiguration<ComponentEventLinkTransformer> configuration)
    {
        configuration.addInstance("csrf", CsrfComponentEventLinkTransformer.class, "after:*");
    }

    @Contribute(ComponentEventRequestHandler.class)
    public static void addCsrfFilter(OrderedConfiguration<ComponentEventRequestFilter> configuration)
    {
        configuration.addInstance("csrf", CsrfProtectionFilter.class, "before:*");
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(CsrfConstants.CSRF_PROTECTION_MODE, CsrfProtectionMode.AUTO.name());
        configuration.add(CsrfConstants.CSRF_TOKEN_PARAMETER_NAME, CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME);
    }
}
