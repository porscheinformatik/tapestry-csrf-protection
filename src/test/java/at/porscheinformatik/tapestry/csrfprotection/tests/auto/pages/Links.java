package at.porscheinformatik.tapestry.csrfprotection.tests.auto.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.EventLink;
import at.porscheinformatik.tapestry.csrfprotection.util.TestBean;

/**
 * Test page for action link and event link components in combination with cross-site request forgery protection.
 */
public class Links
{
    @Property
    @Persist
    private TestBean testBean;

    @Component(parameters = {"event=event"})
    private EventLink eventlink1, eventlink2;

    void onAction()
    {
        testBean.setTestProperty("onAction!");
    }

    void onEvent()
    {
        testBean.setTestProperty("onEvent!");
    }

    void onActivate()
    {
        if (testBean == null)
        {
            testBean = new TestBean();
        }
    }
}
