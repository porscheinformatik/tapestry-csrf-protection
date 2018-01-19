package at.porscheinformatik.tapestry.csrfprotection.tests.auto.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import at.porscheinformatik.tapestry.csrfprotection.util.TestBean;

/**
 * This page contains just a simple BeanEditForm, which is tested in combination with the cross-site request forgery
 * protection.
 */
public class BeanEditForm
{
    @Persist
    @Property
    private TestBean testBean;

    void onActivate()
    {
        if (testBean == null)
        {
            testBean = new TestBean("test");
        }
    }
}
