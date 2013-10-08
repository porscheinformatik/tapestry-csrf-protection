package org.apache.tapestry5.csrfprotection.internal;

import java.security.SecureRandom;

import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.csrfprotection.CsrfException;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains the logic to create a secure token for cross-site script request forgery protection. It is
 * created for a HTTP session and provides the same token throughout the whole session (per-session paradigm).
 */
public class CsrfTokenProvider
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CsrfTokenProvider.class);

    private final SecureRandom secureRandom = new SecureRandom();

    private final ApplicationStateManager applicationStateManager;

    /**
     * Initializes the secure token that will stay the same for the whole life cycle of this instance.
     * 
     * @param applicationStateManager {@link ApplicationStateManager}
     */
    public CsrfTokenProvider(ApplicationStateManager applicationStateManager)
    {
        this.applicationStateManager = applicationStateManager;
    }

    /**
     * Returns the token stored in this instance.
     * 
     * @return the stored token or a newly generated one
     */
    public String getSessionToken()
    {
        String sessionToken = getServerToken(applicationStateManager);
        if (sessionToken == null)
        {
            sessionToken = String.valueOf(secureRandom.nextInt(Integer.MAX_VALUE));
            applicationStateManager.set(CsrfTokenHolder.class, new CsrfTokenHolder(sessionToken));
        }
        return sessionToken;
    }

    /**
     * This method performs the check of the token. It extracts the current client token from the request and the
     * current server-side token by accessing the CsrfTokenProvider instance assigned in this session.
     * 
     * @param request .
     * @param applicationStateManager .
     * @throws CsrfException when token not there or token does not match
     */
    public static void checkToken(Request request, ApplicationStateManager applicationStateManager)
        throws CsrfException
    {
        String requestParam = getClientToken(request);
        String serverToken = getServerToken(applicationStateManager);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("SessionToken: " + serverToken + ", ClientToken: " + requestParam);
        }

        if (serverToken == null || !serverToken.equals(requestParam))
        {
            LOGGER.warn("CSRF Attack detected. Server-Token: {}  vs. Client-Token: {}",
                CsrfTokenProvider.getServerToken(applicationStateManager), CsrfTokenProvider.getClientToken(request));

            throw new CsrfException("CSRF Attack detected. Invalid client token: "
                + CsrfTokenProvider.getClientToken(request));
        }
    }

    /**
     * Extracts the client token form a reequest.
     * 
     * @param request Request that contains the client token
     * @return Secure Anti-Csrf token.
     */
    public static String getClientToken(Request request)
    {
        return request.getParameter(CsrfConstants.TOKEN_NAME);
    }

    /**
     * Extracts the server-side token from the current application state.
     * 
     * @param applicationStateManager The CsrfTokenProvider instance is stored in the session.
     * @return Secure Anti-CSRF token or null
     */
    public static String getServerToken(ApplicationStateManager applicationStateManager)
    {
        CsrfTokenHolder csrfTokenHolder = applicationStateManager.getIfExists(CsrfTokenHolder.class);
        return csrfTokenHolder == null ? null : csrfTokenHolder.getToken();
    }
}
