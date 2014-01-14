package org.apache.tapestry5.csrfprotection.tests.auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.csrfprotection.CsrfConstants;
import org.apache.tapestry5.csrfprotection.services.CsrfProtectionModule;
import org.apache.tapestry5.csrfprotection.tests.auto.services.AppModule;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.formos.tapestry.xpath.TapestryXPath;

/**
 * Tests the BeanEditForm in combination with the cross-site request forgery protection.
 */
public class BeanEditFormTest extends Assert
{
    @Test
    /**
     * A page that contains a form is tested. It should contain the CSRF protection token.
     */
    public void testForTokenPresentAutoMode()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        String appName = "AutoMode";

        PageTester tester =
            new PageTester(appPackage, appName, "src/test/webapp", CsrfProtectionModule.class, AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester
            .renderPage("BeanEditForm");
        try
        {
            List<Element> selectElements = TapestryXPath.xpath(
                "id('form')//input").selectElements(doc);
            boolean found = false;
            for (Element elem : selectElements)
            {
                if (elem.getAttribute("name") != null
                    && elem.getAttribute("name").equals(
                        CsrfConstants.DEFAULT_CSRF_TOKEN_PARAMETER_NAME))
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
    /**
     * A page that contains a form is tested. A submit of the form should be possible without any problem.
     */
    public void testSumbit()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.off";
        String appName = "OffMode";

        PageTester tester =
            new PageTester(appPackage, appName, "src/test/webapp", CsrfProtectionModule.class, AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester
            .renderPage("BeanEditForm");
        Element form = doc.getElementById("form");
        Map<String, String> test = new HashMap<String, String>();
        String updateValue = "udpatedValue";
        test.put("testProperty", updateValue);
        doc = tester.submitForm(form, test);
        try
        {
            List<Element> elements = TapestryXPath.xpath("id('testProperty')").selectElements(doc);
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
