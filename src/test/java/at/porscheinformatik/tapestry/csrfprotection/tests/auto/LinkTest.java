package at.porscheinformatik.tapestry.csrfprotection.tests.auto;

import static at.porscheinformatik.tapestry.csrfprotection.CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME;

import java.util.List;

import at.porscheinformatik.tapestry.csrfprotection.util.PageTesterUtils;
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
    /**
     * Test if the token is present in the event links.
     * 
     * @throws JaxenException .
     */
    @Test
    public void testForTokenPresent() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Links");

        List<Element> selectElements = TapestryXPath.xpath("//a").selectElements(doc);

        for (Element elem : selectElements)
        {
            if (elem.getAttribute("href") != null)
            {
                String href = elem.getAttribute("href");
                if (href.contains("actionlink") || href.contains("event"))
                {
                    if (!href.contains(DEFAULT_CSRF_TOKEN_PARAMETER_NAME))
                    {
                        fail("Cross-site request forgery token not present. It should be there as link parameter!");
                    }

                }
            }
        }
    }

    /**
     * The action link should still work.
     * 
     * @throws JaxenException .
     */
    @Test
    public void testActionLink() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

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
     * @throws JaxenException .
     */
    @Test
    public void testEventLink() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

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
