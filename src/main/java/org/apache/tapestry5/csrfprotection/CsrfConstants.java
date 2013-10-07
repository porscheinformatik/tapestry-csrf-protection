package org.apache.tapestry5.csrfprotection;

/**
 * Constants for CSRF protection module.
 */
public final class CsrfConstants
{
    public static final String TOKEN_NAME = "t:csrf_nonce";

    public static final String CSRF_PROTECTION_MODE = "tapestry.csrf-protection-mode";

    private CsrfConstants()
    {
    }
}
