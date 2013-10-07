package org.apache.tapestry5.csrfprotection.internal;

public class CsrfTokenHolder
{
    private final String token;

    public CsrfTokenHolder(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }
}
