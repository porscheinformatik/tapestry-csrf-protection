package at.porscheinformatik.tapestry.csrfprotection;

/**
 * This exception is thrown if a cross-site request forgery attack is detected.
 */
public class CsrfException extends RuntimeException
{
    /**
     * Created serial id.
     */
    private static final long serialVersionUID = 9041230161539868440L;

    /**
     * @param msg .
     */
    public CsrfException(String msg)
    {
        super(msg);
    }
}
