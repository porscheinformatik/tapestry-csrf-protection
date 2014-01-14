package org.apache.tapestry5.csrfprotection.tests.off;

import static org.apache.tapestry5.csrfprotection.CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME;

import java.util.List;

import org.apache.tapestry5.csrfprotection.services.CsrfProtectionModule;
import org.apache.tapestry5.csrfprotection.tests.off.services.AppModule;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Text;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.formos.tapestry.xpath.TapestryXPath;

/**
 * Test for the action and event link component for the auto mode.
 */
public class LinkTest extends Assert
{
    @Test
    public void testForTokenPresentAutoMode() throws JaxenException
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.off";
        String appName = "OffMode";
        PageTester tester = new PageTester(appPackage, appName,
            "src/test/webapp", CsrfProtectionModule.class,
            AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Links");

        List<Element> selectElements = TapestryXPath.xpath("//a").selectElements(doc);
        for (Element elem : selectElements)
        {
            if (elem.getAttribute("href") != null)
            {
                String href = elem.getAttribute("href");
                if (href.contains("actionlink") || href.contains("event"))
                {
                    if (href.contains(DEFAULT_CSRF_TOKEN_PARAMETER_NAME))
                    {
                        fail("Cross-site request forgery should not be present!");
                    }
                }
            }
        }
    }

    /**
     * The action link should still work.
     */
    @Test
    public void testActionLink() throws JaxenException
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.off";
        String appName = "OffMode";
        PageTester tester = new PageTester(appPackage, appName,
            "src/test/webapp", CsrfProtectionModule.class,
            AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Links");

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
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.off";
        String appName = "OffMode";
        PageTester tester = new PageTester(appPackage, appName,
            "src/main/webapp", CsrfProtectionModule.class,
            AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Links");
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
