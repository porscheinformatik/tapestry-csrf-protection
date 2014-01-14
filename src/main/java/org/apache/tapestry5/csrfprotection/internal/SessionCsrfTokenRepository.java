package org.apache.tapestry5.csrfprotection.internal;

import java.util.UUID;

import org.apache.tapestry5.csrfprotection.CsrfToken;
import org.apache.tapestry5.csrfprotection.services.CsrfTokenRepository;
import org.apache.tapestry5.services.ApplicationStateManager;

public class SessionCsrfTokenRepository implements CsrfTokenRepository
{
    private final ApplicationStateManager applicationStateManager;

    public SessionCsrfTokenRepository(ApplicationStateManager applicationStateManager)
    {
        super();
        this.applicationStateManager = applicationStateManager;
    }

    public CsrfToken generateToken()
    {
        return new DefaultCsrfToken(UUID.randomUUID().toString(), "_csrf");
    }

    public CsrfToken loadToken()
    {
        return applicationStateManager.getIfExists(CsrfToken.class);
    }

    public void saveToken(CsrfToken token)
    {
        applicationStateManager.set(CsrfToken.class, token);
    }
}
