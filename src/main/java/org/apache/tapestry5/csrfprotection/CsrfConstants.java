package org.apache.tapestry5.csrfprotection;

/**
 * Constants for CSRF protection module.
 */
public final class CsrfConstants
{
    public static final String CSRF_TOKEN_PARAMETER_NAME = "tapestry.csrf-token-parameter-name";
    
    public static final String DEFAULT_CSRF_TOKEN_PARAMETER_NAME = "_csrf";

    public static final String CSRF_PROTECTION_MODE = "tapestry.csrf-protection-mode";

    private CsrfConstants()
    {
    }
}
