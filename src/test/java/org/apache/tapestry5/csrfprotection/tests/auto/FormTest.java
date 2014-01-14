package org.apache.tapestry5.csrfprotection.tests.auto;

import static org.apache.tapestry5.csrfprotection.CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.csrfprotection.services.CsrfProtectionModule;
import org.apache.tapestry5.csrfprotection.tests.auto.services.AppModule;
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
     */
    @Test
    public void testForTokenPresent()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        String appName = "AutoMode";
        PageTester tester =
            new PageTester(appPackage, appName, "src/test/webapp", CsrfProtectionModule.class, AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Form");
        try
        {
            List<Element> selectElements = TapestryXPath.xpath("id('messageForm')//input").selectElements(doc);
            boolean found = false;

            for (Element elem : selectElements)
            {
                if (elem.getAttribute("name") != null && elem.getAttribute("name").equals(DEFAULT_CSRF_TOKEN_PARAMETER_NAME))
                {
                    found = true;
                }
            }
            if (!found)
            {
                fail("Cross-site request forgery token not present. It should be there as a hidden input!");
            }

        }
        catch (JaxenException e)
        {
            // TODO Auto-generated catch block
            fail("JaxenException", e);
        }
    }

    @Test
    public void testSubmitForm()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        String appName = "AutoMode";
        PageTester tester =
            new PageTester(appPackage, appName, "src/test/webapp", CsrfProtectionModule.class, AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("Form");
        Element form = doc.getElementById("messageForm");
        Map<String, String> test = new HashMap<String, String>();
        String updateValue = "udpatedValue";
        test.put("message", updateValue);
        doc = tester.submitForm(form, test);
        try
        {
            List<Element> elements = TapestryXPath.xpath("id('message')").selectElements(doc);
            assertTrue(elements.size() == 1, "There should be only one input with id testProperty in the response.");
            String newValue = elements.get(0).getAttribute("value");
            assertTrue(newValue.equals(updateValue), "The submitted change was not updated!");
        }
        catch (JaxenException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
