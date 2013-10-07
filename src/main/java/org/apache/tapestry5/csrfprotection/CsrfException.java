package org.apache.tapestry5.csrfprotection;

/**
 * This exception is thrown if a cross-site request forgery attack is detected.
 */
public class CsrfException extends RuntimeException
{
    /**
     * Created serial id.
     */
    private static final long serialVersionUID = 9041230161539868440L;

    public CsrfException(String msg)
    {
        super(msg);
    }
}
