package org.apache.tapestry5.csrfprotection;

/**
 * Provides the information about an expected CSRF token.
 *
 * @see org.apache.tapestry5.csrfprotection.internal.DefaultCsrfToken
 * @since 1.1
 */
public interface CsrfToken
{
    /**
     * Gets the HTTP parameter name that should contain the token. Cannot be null.
     * 
     * @return the HTTP parameter name that should contain the token.
     */
    String getParameterName();

    /**
     * Gets the token value. Cannot be null.
     * 
     * @return the token value
     */
    String getToken();
}
