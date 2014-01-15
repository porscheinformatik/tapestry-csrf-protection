package org.apache.tapestry5.csrfprotection.tests.auto;

import static org.testng.Assert.assertEquals;

import org.apache.tapestry5.csrfprotection.services.CsrfTokenRepository;
import org.apache.tapestry5.csrfprotection.tests.auto.pages.CsrfHidden;
import org.apache.tapestry5.csrfprotection.util.PageTesterUtils;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.annotations.Test;

public class CsrfHiddenTest
{
    @Test
    public void testCsrfHiddenIsOnPage() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();
        CsrfTokenRepository tokenRepository = tester.getService(CsrfTokenRepository.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage(CsrfHidden.class.getSimpleName());

        Element csrfHiddenElement = doc.getElementById("csrf-hidden");

        assertEquals(csrfHiddenElement.getAttribute("name"), "_csrf");
        assertEquals(csrfHiddenElement.getAttribute("value"), tokenRepository.loadToken().getToken());
    }
}
