package org.apache.tapestry5.csrfprotection.internal;

import org.apache.tapestry5.csrfprotection.CsrfToken;

public class DefaultCsrfToken implements CsrfToken
{
    private final String token;

    private final String parameterName;

    public DefaultCsrfToken(String token, String parameterName)
    {
        super();
        this.token = token;
        this.parameterName = parameterName;
    }

    public String getToken()
    {
        return token;
    }

    public String getParameterName()
    {
        return parameterName;
    }
}
