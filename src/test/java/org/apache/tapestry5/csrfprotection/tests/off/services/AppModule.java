package org.apache.tapestry5.csrfprotection.tests.off.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.csrfprotection.CsrfProtectionMode;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

public class AppModule
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
