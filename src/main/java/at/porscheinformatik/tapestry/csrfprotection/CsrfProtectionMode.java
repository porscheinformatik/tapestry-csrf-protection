package at.porscheinformatik.tapestry.csrfprotection;

/**
 * Modes for CSRF protections
 */
public enum CsrfProtectionMode
{
    /** turn CSRF protection off - for all pages and all components */
    OFF,
    /** activate auto CSRF protection - for all pages and all components */
    AUTO;
}
