package at.porscheinformatik.tapestry.csrfprotection.tests.off.services;

import org.apache.tapestry5.SymbolConstants;
import at.porscheinformatik.tapestry.csrfprotection.CsrfConstants;
import at.porscheinformatik.tapestry.csrfprotection.CsrfProtectionMode;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

public class OffModeModule
{
    public static void bind(ServiceBinder binder)
    {
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        configuration.add(CsrfConstants.CSRF_PROTECTION_MODE, CsrfProtectionMode.OFF.name());
    }
}
