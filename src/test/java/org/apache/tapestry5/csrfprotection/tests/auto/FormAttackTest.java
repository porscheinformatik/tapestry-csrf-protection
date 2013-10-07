package org.apache.tapestry5.csrfprotection.tests.auto;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.csrfprotection.services.CsrfProtectionModule;
import org.apache.tapestry5.csrfprotection.tests.auto.services.AppModule;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Simulated CSRF attack that should be detected.
 */
public class FormAttackTest extends Assert
{
    @Test
    public void testFormAttack()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        String appName = "AutoMode";

        PageTester tester =
            new PageTester(appPackage, appName, "src/test/webapp", CsrfProtectionModule.class, AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("FormAttack");
        Element form = doc.getElementById("messageForm");
        Map<String, String> test = new HashMap<String, String>();
        test.put("message", "updatedMessage");
        doc = tester.submitForm(form, test);
        assertTrue(doc.toString().contains("CSRF Attack detected"));
    }
}
