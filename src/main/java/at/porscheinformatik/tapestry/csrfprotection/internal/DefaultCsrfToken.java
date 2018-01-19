package at.porscheinformatik.tapestry.csrfprotection.internal;

import at.porscheinformatik.tapestry.csrfprotection.CsrfToken;

/**
 * Default {@link CsrfToken} implementation.
 */
public class DefaultCsrfToken implements CsrfToken
{
    private final String headerName;

    private final String parameterName;

    private final String token;

    /**
     * Creates a new instance.
     * 
     * @param headerName the HTTP header name to use
     * @param parameterName the HTTP parameter name to use
     * @param token the value of the token (i.e. expected value of the HTTP parameter of parametername).
     */
    public DefaultCsrfToken(String headerName, String parameterName, String token)
    {
        this.headerName = headerName;
        this.parameterName = parameterName;
        this.token = token;
    }

    public String getHeaderName()
    {
        return headerName;
    }

    public String getParameterName()
    {
        return parameterName;
    }

    public String getToken()
    {
        return token;
    }
}
