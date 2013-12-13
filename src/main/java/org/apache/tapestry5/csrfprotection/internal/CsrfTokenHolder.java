package org.apache.tapestry5.csrfprotection.internal;

import java.io.Serializable;

/**
 * Holds the token in the user session.
 */
public class CsrfTokenHolder implements Serializable
{
    private static final long serialVersionUID = -3238063104558657324L;

    private final String token;

    /**
     * @param token the token to store in the session
     */
    public CsrfTokenHolder(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }
}
