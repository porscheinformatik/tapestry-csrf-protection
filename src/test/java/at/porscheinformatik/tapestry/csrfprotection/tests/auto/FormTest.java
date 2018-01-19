package at.porscheinformatik.tapestry.csrfprotection.tests.auto;

import static at.porscheinformatik.tapestry.csrfprotection.CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.porscheinformatik.tapestry.csrfprotection.util.PageTesterUtils;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.formos.tapestry.xpath.TapestryXPath;

/**
 * Test for the form component, if the auto mode is used.
 */
public class FormTest extends Assert
{

    /**
     * A page that contains a form is tested. It should contain the CSRF protection token.
     * 
     * @throws JaxenException .
     */
    @Test
    public void testForTokenPresent() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Form");

        List<Element> selectElements = TapestryXPath.xpath("id('messageForm')//input").selectElements(doc);
        boolean found = false;

        for (Element elem : selectElements)
        {
            if (elem.getAttribute("name") != null
                && elem.getAttribute("name").equals(DEFAULT_CSRF_TOKEN_PARAMETER_NAME))
            {
                found = true;
            }
        }
        if (!found)
        {
            fail("Cross-site request forgery token not present. It should be there as a hidden input!");
        }

    }

    @Test
    public void testSubmitForm() throws JaxenException
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Form");
        Element form = doc.getElementById("messageForm");
        Map<String, String> test = new HashMap<String, String>();
        String updateValue = "udpatedValue";
        test.put("message", updateValue);
        doc = tester.submitForm(form, test);

        List<Element> elements = TapestryXPath.xpath("id('message')").selectElements(doc);
        assertTrue(elements.size() == 1, "There should be only one input with id testProperty in the response.");
        String newValue = elements.get(0).getAttribute("value");
        assertTrue(newValue.equals(updateValue), "The submitted change was not updated!");

    }
}
