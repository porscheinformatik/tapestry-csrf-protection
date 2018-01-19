package at.porscheinformatik.tapestry.csrfprotection.tests.auto;

import java.util.HashMap;
import java.util.Map;

import at.porscheinformatik.tapestry.csrfprotection.util.PageTesterUtils;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.test.PageTester;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeanEditFormAttackTest extends Assert
{
    @Test
    public void testBeanEditFormAttack()
    {
        PageTester tester = PageTesterUtils.autoModePageTester();

        org.apache.tapestry5.dom.Document doc = tester.renderPage("BeanEditFormAttack");
        Element form = doc.getElementById("form");
        Map<String, String> test = new HashMap<String, String>();
        test.put("testProperty", "test");
        doc = tester.submitForm(form, test);
        assertTrue(doc.toString().contains("CSRF Attack detected"));
    }
}
