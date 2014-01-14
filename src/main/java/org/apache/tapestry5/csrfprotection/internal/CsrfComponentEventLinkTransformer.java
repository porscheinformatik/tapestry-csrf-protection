package org.apache.tapestry5.csrfprotection.internal;

import static org.apache.tapestry5.csrfprotection.CsrfConstants.CSRF_TOKEN_PARAMETER_NAME;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;

/**
 * Adds CSRF token to all component event links.
 */
public class CsrfComponentEventLinkTransformer implements ComponentEventLinkTransformer
{
    private final CsrfTokenManager csrfTokenProvider;
    private final ProtectedPagesService protectedPagesService;
    private final String parameterName;

    /**
     * @param csrfTokenProvider .
     * @param protectedPagesService .
     * @param parameterName symbol {@link CsrfConstants#CSRF_TOKEN_PARAMETER_NAME}
     */
    public CsrfComponentEventLinkTransformer(CsrfTokenManager csrfTokenProvider,
        ProtectedPagesService protectedPagesService, @Symbol(CSRF_TOKEN_PARAMETER_NAME) String parameterName)
    {
        super();
        this.csrfTokenProvider = csrfTokenProvider;
        this.protectedPagesService = protectedPagesService;
        this.parameterName = parameterName;
    }

    /**
     * {@inheritDoc}
     */
    public Link transformComponentEventLink(Link defaultLink, ComponentEventRequestParameters parameters)
    {
        if (protectedPagesService.isPageProtected(parameters))
        {
            return defaultLink.addParameterValue(parameterName, csrfTokenProvider.getSessionToken().getToken());
        }

        return defaultLink;
    }

    /**
     * {@inheritDoc}
     */
    public ComponentEventRequestParameters decodeComponentEventRequest(Request request)
    {
        return null;
    }
}
