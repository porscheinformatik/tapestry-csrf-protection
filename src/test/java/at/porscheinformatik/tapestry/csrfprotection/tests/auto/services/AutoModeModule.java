package at.porscheinformatik.tapestry.csrfprotection.tests.auto.services;

import org.apache.tapestry5.SymbolConstants;
import at.porscheinformatik.tapestry.csrfprotection.CsrfConstants;
import at.porscheinformatik.tapestry.csrfprotection.CsrfProtectionMode;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.LibraryMapping;

/**
 * The integration test contain a module definition for each mode of the cross-site request forgery protection. This
 * module configures the auto mode for the tests.
 */
public class AutoModeModule
{
    public static void bind(ServiceBinder binder)
    {
    }

    public static void contributeApplicationDefaults(
        MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        configuration.add(CsrfConstants.CSRF_PROTECTION_MODE, CsrfProtectionMode.AUTO.name());
    }

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("", "at.porscheinformatik.tapestry.csrfprotection.tests.auto"));
    }
}
