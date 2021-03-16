package at.porscheinformatik.tapestry.csrfprotection.internal;

import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;
import org.apache.tapestry5.services.linktransform.LinkTransformer;

/**
 * Decorator {@link LinkTransformer} calling {@link CsrfComponentEventLinkTransformer} to modify event links.
 */
public final class CsrfLinkTransformerDecorator implements LinkTransformer
{
    private final ComponentEventLinkTransformer csrfComponentEventLinkTransformer;
    private final LinkTransformer delegate;

    /**
     * @param csrfComponentEventLinkTransformer .
     * @param delegate the original {@link LinkTransformer}
     */
    public CsrfLinkTransformerDecorator(ComponentEventLinkTransformer csrfComponentEventLinkTransformer,
        LinkTransformer delegate)
    {
        this.csrfComponentEventLinkTransformer = csrfComponentEventLinkTransformer;
        this.delegate = delegate;
    }

    @Override
    public Link transformPageRenderLink(Link defaultLink, PageRenderRequestParameters parameters)
    {
        return delegate.transformPageRenderLink(defaultLink, parameters);
    }

    @Override
    public PageRenderRequestParameters decodePageRenderRequest(Request request)
    {
        return delegate.decodePageRenderRequest(request);
    }

    @Override
    public Link transformComponentEventLink(Link defaultLink, ComponentEventRequestParameters parameters)
    {
        Link transformedLink = csrfComponentEventLinkTransformer.transformComponentEventLink(defaultLink, parameters);

        return transformedLink != null ? transformedLink : delegate
            .transformComponentEventLink(defaultLink, parameters);
    }

    @Override
    public ComponentEventRequestParameters decodeComponentEventRequest(Request request)
    {
        ComponentEventRequestParameters requestParameters =
            csrfComponentEventLinkTransformer.decodeComponentEventRequest(request);

        return requestParameters != null ? requestParameters : delegate.decodeComponentEventRequest(request);
    }
}