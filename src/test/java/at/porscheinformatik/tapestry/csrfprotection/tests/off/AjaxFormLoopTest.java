package at.porscheinformatik.tapestry.csrfprotection.tests.off;

import static at.porscheinformatik.tapestry.csrfprotection.CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME;

import java.util.List;

import at.porscheinformatik.tapestry.csrfprotection.util.PageTesterUtils;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.formos.tapestry.xpath.TapestryXPath;

/**
 * Tests the BeanEditForm in combination with the cross-site request forgery protection.
 */
public class AjaxFormLoopTest extends Assert
{
    /**
     * A page that contains a form is tested. It should contain the CSRF protection token.
     */
    @Test
    public void testForTokenNotPresent()
    {
        PageTester tester = PageTesterUtils.offModePageTester();

        org.apache.tapestry5.dom.Document doc = tester
            .renderPage("AjaxFormLoop");
        // simple test, just checking if the event link rendered in javascript contains the anti CSRF token
        assertFalse(doc.toString().contains("t:antiCsrfToken"),
            "The antiCsrfToken parameter should not be present in the off mode.");
    }

    /**
     * Tests if the remove row link is still working.
     * 
     * @throws JaxenException .
     */
    @Test
    public void sendRemoveRowLink() throws JaxenException
    {
        PageTester tester = PageTesterUtils.offModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("AjaxFormLoop");

        //obtain anti CSRF token from dummy link	
        List<Element> dummyLinkElements = TapestryXPath.xpath("id('dummy')").selectElements(doc);
        assertTrue(dummyLinkElements.size() == 1,
            "There should be only one dummy link used to extract an anti CSRF token.");

        Element element = dummyLinkElements.get(0);
        String token =
            element.getAttribute("href").substring(
                element.getAttribute("href").indexOf(DEFAULT_CSRF_TOKEN_PARAMETER_NAME)
                    + (DEFAULT_CSRF_TOKEN_PARAMETER_NAME + "=").length());

        List<Element> selectElements = TapestryXPath.xpath("id('removeRowLink')").selectElements(doc);
        assertTrue(selectElements.size() == 1, "There should be only one remove row link.");

        Element removeRowLink = selectElements.get(0);
        String href = removeRowLink.getAttribute("href");
        href += "?" + DEFAULT_CSRF_TOKEN_PARAMETER_NAME + "=" + token;
        removeRowLink.attribute("href", href);
        Document response = tester.clickLink(removeRowLink);

        assertTrue(response.toString().contains("A component event handler method returned the value {}"),
            "AjaxFormLoop component should does not work properly anymore (removeRowLink).");
    }
}
