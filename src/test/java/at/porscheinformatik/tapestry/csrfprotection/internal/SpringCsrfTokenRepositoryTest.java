package at.porscheinformatik.tapestry.csrfprotection.internal;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.testng.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.porscheinformatik.tapestry.csrfprotection.CsrfToken;
import org.easymock.EasyMock;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.testng.annotations.Test;

public class SpringCsrfTokenRepositoryTest
{
    private static final DefaultCsrfToken SPRING_TOKEN = new org.springframework.security.web.csrf.DefaultCsrfToken(
        "CSRF-HEADER", "_csrf", "CSRF-TOKEN-XXX");

    @Test
    public void generateToken()
    {
        SpringCsrfTokenRepository csrfTokenRepository = createSpringTokenRepo();

        CsrfToken tapestryToken = csrfTokenRepository.generateToken();

        assertEquals(tapestryToken.getHeaderName(), SPRING_TOKEN.getHeaderName());
        assertEquals(tapestryToken.getParameterName(), SPRING_TOKEN.getParameterName());
        assertEquals(tapestryToken.getToken(), SPRING_TOKEN.getToken());
    }

    @Test
    public void loadToken()
    {
        SpringCsrfTokenRepository csrfTokenRepository = createSpringTokenRepo();

        CsrfToken tapestryToken = csrfTokenRepository.loadToken();

        assertEquals(tapestryToken.getHeaderName(), SPRING_TOKEN.getHeaderName());
        assertEquals(tapestryToken.getParameterName(), SPRING_TOKEN.getParameterName());
        assertEquals(tapestryToken.getToken(), SPRING_TOKEN.getToken());
    }

    private static SpringCsrfTokenRepository createSpringTokenRepo()
    {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        HttpServletResponse response = createNiceMock(HttpServletResponse.class);
        CsrfTokenRepository springCsrfTokenRepository = createNiceMock(CsrfTokenRepository.class);
        expect(springCsrfTokenRepository.generateToken(EasyMock.anyObject(HttpServletRequest.class)))
            .andReturn(SPRING_TOKEN);
        expect(springCsrfTokenRepository.loadToken(EasyMock.anyObject(HttpServletRequest.class)))
            .andReturn(SPRING_TOKEN);
        replay(request, response, springCsrfTokenRepository);
        SpringCsrfTokenRepository csrfTokenRepository =
            new SpringCsrfTokenRepository(springCsrfTokenRepository, request, response);
        return csrfTokenRepository;
    }
}
