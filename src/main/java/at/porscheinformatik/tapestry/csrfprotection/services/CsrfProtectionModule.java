package at.porscheinformatik.tapestry.csrfprotection.services;

import at.porscheinformatik.tapestry.csrfprotection.CsrfConstants;
import at.porscheinformatik.tapestry.csrfprotection.CsrfProtectionMode;
import at.porscheinformatik.tapestry.csrfprotection.internal.CsrfComponentEventLinkTransformer;
import at.porscheinformatik.tapestry.csrfprotection.internal.CsrfLinkTransformerDecorator;
import at.porscheinformatik.tapestry.csrfprotection.internal.CsrfProtectionFilter;
import at.porscheinformatik.tapestry.csrfprotection.internal.CsrfTokenManager;
import at.porscheinformatik.tapestry.csrfprotection.internal.ProtectedPagesService;
import at.porscheinformatik.tapestry.csrfprotection.internal.SessionCsrfTokenRepository;
import at.porscheinformatik.tapestry.csrfprotection.internal.SpringContextHelper;
import at.porscheinformatik.tapestry.csrfprotection.internal.SpringCsrfTokenRepository;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.services.ComponentEventRequestFilter;
import org.apache.tapestry5.services.ComponentEventRequestHandler;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;
import org.apache.tapestry5.services.linktransform.LinkTransformer;

/**
 * IoC module for Tapestry CSRF Protection. CHECKSTYLE:OFF
 */
public class CsrfProtectionModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(CsrfTokenManager.class);
        binder.bind(ProtectedPagesService.class);
        binder.bind(ComponentEventLinkTransformer.class, CsrfComponentEventLinkTransformer.class).withSimpleId();
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("csrf", "at.porscheinformatik.tapestry.csrfprotection"));
    }

    public static CsrfTokenRepository buildCsrfTokenRepository(ObjectLocator objectLocator)
    {
        // check if spring is there and if CsrfTokenRepository is registered
        if (SpringContextHelper.getSpringBean(objectLocator,
            "org.springframework.security.web.csrf.CsrfTokenRepository") != null)
        {
            return objectLocator.proxy(CsrfTokenRepository.class, SpringCsrfTokenRepository.class);
        }
        else
        {
            return objectLocator.proxy(CsrfTokenRepository.class, SessionCsrfTokenRepository.class);
        }
    }

    @Decorate(serviceInterface = LinkTransformer.class)
    @Match("LinkTransformer")
    public static LinkTransformer decorateLinkTransformer(Class<LinkTransformer> serviceInterface,
        final LinkTransformer delegate, String serviceId,
        final @Local ComponentEventLinkTransformer csrfComponentEventLinkTransformer)
    {
        return new CsrfLinkTransformerDecorator(csrfComponentEventLinkTransformer, delegate);
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
