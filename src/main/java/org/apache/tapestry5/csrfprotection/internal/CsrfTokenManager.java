package org.apache.tapestry5.csrfprotection.internal;

import static org.apache.tapestry5.csrfprotection.CsrfConstants.CSRF_TOKEN_PARAMETER_NAME;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.csrfprotection.CsrfException;
import org.apache.tapestry5.csrfprotection.CsrfToken;
import org.apache.tapestry5.csrfprotection.services.CsrfTokenRepository;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains the logic to create a secure token for cross-site script request forgery protection. It is
 * created for a HTTP session and provides the same token throughout the whole session (per-session paradigm).
 */
public class CsrfTokenManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CsrfTokenManager.class);

    private final CsrfTokenRepository tokenRepository;

    private final String parameterName;

    /**
     * Initializes the secure token that will stay the same for the whole life cycle of this instance.
     * 
     * @param tokenProvider an implementation of {@link CsrfTokenRepository}
     * @param parameterName symbol {@link CsrfConstants#CSRF_TOKEN_PARAMETER_NAME}
     */
    public CsrfTokenManager(CsrfTokenRepository tokenProvider, @Symbol(CSRF_TOKEN_PARAMETER_NAME) String parameterName)
    {
        this.tokenRepository = tokenProvider;
        this.parameterName = parameterName;
    }

    /**
     * Returns the token stored in this instance.
     * 
     * @return the stored token or a newly generated one
     */
    public CsrfToken getSessionToken()
    {
        CsrfToken sessionToken = tokenRepository.loadToken();
        if (sessionToken == null)
        {
            sessionToken = tokenRepository.generateToken();
            tokenRepository.saveToken(sessionToken);
        }
        return sessionToken;
    }

    /**
     * This method performs the check of the token. It extracts the current client token from the request and the
     * current server-side token by accessing the CsrfTokenProvider instance assigned in this session.
     * 
     * @param request .
     * @throws CsrfException when token not there or token does not match
     */
    public void checkToken(HttpServletRequest request)
        throws CsrfException
    {
        String requestParam = request.getParameter(parameterName);
        CsrfToken serverToken = tokenRepository.loadToken();

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("SessionToken: " + serverToken + ", ClientToken: " + requestParam);
        }

        if (serverToken == null || !serverToken.getToken().equals(requestParam))
        {
            // check if session id changed - if yes Spring Security (or another security framework) 
            // requested a new session after login
            HttpSession session = request.getSession(false);
            if (session != null && !session.getId().equals(request.getRequestedSessionId()))
            {
                return;
            }

            LOGGER.warn("CSRF Attack detected. Server-Token: {}  vs. Client-Token: {}",
                serverToken, requestParam);

            throw new CsrfException("CSRF Attack detected. Invalid client token: " + requestParam);
        }
    }
}
