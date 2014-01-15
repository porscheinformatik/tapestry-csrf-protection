package org.apache.tapestry5.csrfprotection.tests.auto;

import static org.apache.tapestry5.csrfprotection.CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME;

import java.util.List;

import org.apache.tapestry5.csrfprotection.util.PageTesterUtils;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Text;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.formos.tapestry.xpath.TapestryXPath;

/**
 * Tests the Unprotected annotation used for pages in combination with the auto mode. The anti CSRF token should not be
 * present and unprotected requests should be possible.
 */
public class UnprotectedPageTest extends Assert
{
    /**
     * Test if the token is present in the event links.
     */
    @Test
    public void testForTokenPresent()
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Unprotected");
        assertFalse(doc.toString().contains(DEFAULT_CSRF_TOKEN_PARAMETER_NAME),
            "The anti CSRF token should not be present.");
    }

    /**
     * The action link should still work.
     */
    @Test
    public void testActionLink() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Unprotected");

        List<Element> list = TapestryXPath.xpath("id('actionLink')").selectElements(doc);
        assertTrue(list.size() == 1, "There should be only one link with id actionLink");
        Element actionLink = list.get(0);
        Document response = tester.clickLink(actionLink);
        List<Element> selectElements = TapestryXPath.xpath("//dd[@class='testProperty']").selectElements(response);
        assertTrue(selectElements.size() == 1,
            "There should be only one dd element with attribute class equal testProperty");
        Element element = selectElements.get(0);
        Text text = (Text) element.getChildren().get(0);
        assertTrue(text.toString().equals("onAction!"),
            "Text that is set by the actionLink is not present! The actionLink component is not working properly");
    }

    /**
     * The event link should work.
     */
    @Test
    public void testEventLink() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Unprotected");

        List<Element> list = TapestryXPath.xpath("id('eventLink1')").selectElements(doc);
        assertTrue(list.size() == 1, "There should be only one link with id eventLink1");
        Element actionLink = list.get(0);
        Document response = tester.clickLink(actionLink);
        List<Element> selectElements = TapestryXPath.xpath("//dd[@class='testProperty']").selectElements(response);
        assertTrue(selectElements.size() == 1,
            "There should be only one dd element with attribute class equal testProperty");
        Element element = selectElements.get(0);
        Text text = (Text) element.getChildren().get(0);
        assertTrue(text.toString().equals("onEvent!"),
            "Text that is set by the eventLink is not present! The eventLink component is not working properly");

    }
}
