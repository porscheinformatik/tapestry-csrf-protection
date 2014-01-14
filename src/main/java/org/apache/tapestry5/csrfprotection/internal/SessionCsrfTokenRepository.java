package org.apache.tapestry5.csrfprotection.internal;

import java.util.UUID;

import org.apache.tapestry5.csrfprotection.CsrfToken;
import org.apache.tapestry5.csrfprotection.services.CsrfTokenRepository;
import org.apache.tapestry5.services.ApplicationStateManager;

/**
 * A {@link CsrfTokenRepository} that stores the {@link CsrfToken} in the HTTP session.
 */
public class SessionCsrfTokenRepository implements CsrfTokenRepository
{
    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";

    private final ApplicationStateManager applicationStateManager;

    /**
     * @param applicationStateManager .
     */
    public SessionCsrfTokenRepository(ApplicationStateManager applicationStateManager)
    {
        super();
        this.applicationStateManager = applicationStateManager;
    }

    @Override
    public CsrfToken generateToken()
    {
        return new DefaultCsrfToken(DEFAULT_CSRF_HEADER_NAME, DEFAULT_CSRF_PARAMETER_NAME, 
            UUID.randomUUID().toString());
    }

    @Override
    public CsrfToken loadToken()
    {
        return applicationStateManager.getIfExists(CsrfToken.class);
    }

    @Override
    public void saveToken(CsrfToken token)
    {
        applicationStateManager.set(CsrfToken.class, token);
    }
}
