package org.apache.tapestry5.csrfprotection.internal;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;

/**
 * Adds CSRF token to all component event links.
 */
public class CsrfComponentEventLinkTransformer implements ComponentEventLinkTransformer
{
    private final CsrfTokenProvider csrfTokenProvider;
    private final ProtectedPagesService protectedPagesService;

    public CsrfComponentEventLinkTransformer(CsrfTokenProvider csrfTokenProvider,
        ProtectedPagesService protectedPagesService)
    {
        super();
        this.csrfTokenProvider = csrfTokenProvider;
        this.protectedPagesService = protectedPagesService;
    }

    public Link transformComponentEventLink(Link defaultLink, ComponentEventRequestParameters parameters)
    {
        if (protectedPagesService.isPageProtected(parameters))
        {
            return defaultLink.addParameterValue(CsrfConstants.TOKEN_NAME, csrfTokenProvider.getSessionToken());
        }

        return defaultLink;
    }

    public ComponentEventRequestParameters decodeComponentEventRequest(Request request)
    {
        return null;
    }
}
