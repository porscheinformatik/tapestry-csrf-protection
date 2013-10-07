package org.apache.tapestry5.csrfprotection.tests.auto;

import java.util.List;

import org.apache.tapestry5.csrfprotection.services.CsrfProtectionModule;
import org.apache.tapestry5.csrfprotection.tests.auto.services.AppModule;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.formos.tapestry.xpath.TapestryXPath;

/**
 * Simulates CSRF attacks on the ActionLink and the EventLink component.
 */
public class LinkAttackTest extends Assert
{
    /**
     * Performs a test attack against the EventLink component.
     */
    @Test
    public void testAttackEventLink() throws JaxenException
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        String appName = "AutoMode";
        PageTester tester = new PageTester(appPackage, appName,
            "src/test/webapp", CsrfProtectionModule.class,
            AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("LinksAttack");
        List<Element> list;
        list = TapestryXPath.xpath("id('eventLink1')").selectElements(doc);
        assertTrue(list.size() == 1, "There should be only one link with id eventLink1");
        Element actionLink = list.get(0);
        Document response = tester.clickLink(actionLink);
        assertTrue(response.toString().contains("CSRF Attack detected"));
    }

    /**
     * Performs a test attack against the ActionLink component.
     */
    @Test
    public void testAttackActionLink() throws JaxenException
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        String appName = "AutoMode";
        PageTester tester = new PageTester(appPackage, appName,
            "src/test/webapp", CsrfProtectionModule.class,
            AppModule.class);

        org.apache.tapestry5.dom.Document doc = tester.renderPage("LinksAttack");
        List<Element> list;
        list = TapestryXPath.xpath("id('eventLink1')").selectElements(doc);
        assertTrue(list.size() == 1, "There should be only one link with id eventLink1");
        Element actionLink = list.get(0);
        Document response = tester.clickLink(actionLink);
        assertTrue(response.toString().contains("CSRF Attack detected"));
    }
}
