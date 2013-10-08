package org.apache.tapestry5.csrfprotection.internal;

/**
 * Holds the token in the user session.
 */
public class CsrfTokenHolder
{
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
