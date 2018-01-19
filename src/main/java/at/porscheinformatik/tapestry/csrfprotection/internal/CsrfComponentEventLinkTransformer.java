package at.porscheinformatik.tapestry.csrfprotection.internal;

import static at.porscheinformatik.tapestry.csrfprotection.CsrfConstants.CSRF_TOKEN_PARAMETER_NAME;

import org.apache.tapestry5.Link;
import at.porscheinformatik.tapestry.csrfprotection.CsrfConstants;
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
    public CsrfComponentEventLinkTransformer(
        CsrfTokenManager csrfTokenProvider,
        ProtectedPagesService protectedPagesService,
        @Symbol(CSRF_TOKEN_PARAMETER_NAME) String parameterName)
    {
        super();
        this.csrfTokenProvider = csrfTokenProvider;
        this.protectedPagesService = protectedPagesService;
        this.parameterName = parameterName;
    }

    @Override
    public Link transformComponentEventLink(Link defaultLink, ComponentEventRequestParameters parameters)
    {
        if (protectedPagesService.isPageProtected(parameters))
        {
            return defaultLink.addParameterValue(parameterName, csrfTokenProvider.getSessionToken().getToken());
        }

        return null;
    }

    @Override
    public ComponentEventRequestParameters decodeComponentEventRequest(Request request)
    {
        return null;
    }
}
