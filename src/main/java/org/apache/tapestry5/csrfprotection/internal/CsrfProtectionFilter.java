package org.apache.tapestry5.csrfprotection.internal;

import java.io.IOException;

import org.apache.tapestry5.services.ComponentEventRequestFilter;
import org.apache.tapestry5.services.ComponentEventRequestHandler;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Request;

/**
 * This filter checks each component event request or page render request for cross-site request forgery attacks. If a
 * page is marked as protected a request is checked. If the auto mode for the cross-stie request forgery protection is
 * used, all pages are checked except those which are marked as unprotected.
 */
public class CsrfProtectionFilter implements ComponentEventRequestFilter
{
    private final CsrfTokenManager csrfTokenManager;
    private final Request request;
    private final ProtectedPagesService protectedPagesService;

    /**
     * Creates a new filter and injects the required services and configuration parameters.
     * 
     * @param csrfTokenManager .
     * @param protectedPagesService .
     * @param request .
     */
    public CsrfProtectionFilter(
        CsrfTokenManager csrfTokenManager,
        ProtectedPagesService protectedPagesService,
        Request request)
    {
        super();
        this.csrfTokenManager = csrfTokenManager;
        this.protectedPagesService = protectedPagesService;
        this.request = request;
    }

    /**
     * Handles a component event request and evaluates the cross-site request forgery protection.
     * 
     * @param parameters .
     * @param handler .
     * @throws IOException when delegate throws
     */
    @Override
    public void handle(ComponentEventRequestParameters parameters, ComponentEventRequestHandler handler)
        throws IOException
    {
        if (protectedPagesService.isPageProtected(parameters))
        {
            csrfTokenManager.checkToken(request);
        }

        handler.handle(parameters);
    }
}
