package org.apache.tapestry5.csrfprotection.components;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.csrfprotection.internal.CsrfTokenManager;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * Hidden field containing the CSRF token to integrate with other frameworks (e.g. Spring Security).
 * <p>
 * HINT: this field is not necessary if you are posting to a Tapestry form.
 */
@SupportsInformalParameters
public class CsrfHidden implements ClientElement
{
    /**
     * Name for the field can be overridden to interact with other frameworks/sites.
     */
    @Parameter(required = true)
    private String name;

    @Inject
    private CsrfTokenManager tokenManager;

    @Inject
    private ComponentResources resources;

    @Environmental
    private JavaScriptSupport jsSupport;

    private String clientId;

    private Element hiddenInputElement;

    String defaultName()
    {
        return tokenManager.getSessionToken().getParameterName();
    }

    boolean beginRender(MarkupWriter writer)
    {
        hiddenInputElement =
            writer.element("input", "type", "hidden", "name", name, "value", tokenManager.getSessionToken().getToken());

        resources.renderInformalParameters(writer);

        writer.end();

        return false;
    }

    @Override
    public String getClientId()
    {
        if (clientId == null)
        {
            clientId = jsSupport.allocateClientId(resources);
            hiddenInputElement.forceAttributes("id", clientId);
        }

        return clientId;
    }
}
