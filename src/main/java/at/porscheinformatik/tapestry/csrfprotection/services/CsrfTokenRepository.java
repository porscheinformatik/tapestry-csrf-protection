package at.porscheinformatik.tapestry.csrfprotection.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import at.porscheinformatik.tapestry.csrfprotection.CsrfToken;

/**
 * Generates CSRF tokens.
 * <p>
 * Note that the tokens should be generated in safe way - that means they should not be guessable.
 */
public interface CsrfTokenRepository
{
    /**
     * @return a newly generated CSRF token
     */
    CsrfToken generateToken();

    /**
     * Saves the {@link CsrfToken} using the {@link HttpServletRequest} and {@link HttpServletResponse}. If the
     * {@link CsrfToken} is null, it is the same as deleting it.
     *
     * @param token the {@link CsrfToken} to save or null to delete
     */
    void saveToken(CsrfToken token);

    /**
     * Loads the expected {@link CsrfToken} from the {@link HttpServletRequest}
     *
     * @return the {@link CsrfToken} or null if none exists
     */
    CsrfToken loadToken();
}
