package at.porscheinformatik.tapestry.csrfprotection.internal;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import at.porscheinformatik.tapestry.csrfprotection.CsrfException;
import at.porscheinformatik.tapestry.csrfprotection.internal.CsrfTokenManager;
import at.porscheinformatik.tapestry.csrfprotection.internal.DefaultCsrfToken;
import at.porscheinformatik.tapestry.csrfprotection.services.CsrfTokenRepository;
import org.apache.tapestry5.internal.test.TestableRequest;
import org.apache.tapestry5.internal.test.TestableRequestImpl;
import org.testng.annotations.Test;

public class CsrfTokenManagerTest
{
    private TestableRequest request = new TestableRequestImpl();

    @Test
    public void noTokenNewSession()
    {
        createTokenManager().checkToken(request, createHttpServletRequest(true));
    }

    @Test(expectedExceptions = CsrfException.class)
    public void noTokenExistingSession()
    {
        createTokenManager().checkToken(request, createHttpServletRequest(false));
    }

    private HttpServletRequest createHttpServletRequest(boolean newSession)
    {
        HttpServletRequest httpServletRequest = createNiceMock(HttpServletRequest.class);
        HttpSession session = createNiceMock(HttpSession.class);
        expect(session.isNew()).andReturn(newSession);
        expect(httpServletRequest.getSession(false)).andReturn(session);
        replay(httpServletRequest, session);
        return httpServletRequest;
    }

    private static CsrfTokenManager createTokenManager()
    {
        CsrfTokenRepository tokenRepository = createNiceMock(CsrfTokenRepository.class);
        expect(tokenRepository.loadToken()).andReturn(new DefaultCsrfToken("_csrf", "_csrf", "CSRF-TOKEN"));
        CsrfTokenManager manager = new CsrfTokenManager(tokenRepository, "_csrf");
        replay(tokenRepository);
        return manager;
    }
}
