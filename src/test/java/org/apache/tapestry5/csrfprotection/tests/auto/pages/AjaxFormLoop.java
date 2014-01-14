package org.apache.tapestry5.csrfprotection.tests.auto.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.csrfprotection.util.TestBean;

/**
 * Page that displays a AjaxFormLoop component with a list of dummy values.
 */
public class AjaxFormLoop
{
    @Property
    @Persist
    private List<TestBean> myList;

    @Property
    private TestBean testBean;

    @SuppressWarnings("rawtypes")
    public ValueEncoder getEncoder()
    {
        return new ValueEncoder<TestBean>()
        {

            public String toClient(TestBean value)
            {
                return value.getTestProperty();
            }

            public TestBean toValue(String keyAsString)
            {
                return new TestBean(keyAsString);
            }
        };
    }

    void onActivate()
    {
        if (myList == null)
        {
            myList = new ArrayList<TestBean>();
            myList.add(new TestBean("bla"));
            myList.add(new TestBean("test"));
            myList.add(new TestBean("foo"));
        }
    }

    @Log
    void onRemoveRow(TestBean bean)
    {
        myList.remove(bean);
    }

    @Log
    TestBean onAddRow()
    {
        TestBean newBean = new TestBean("new");
        myList.add(newBean);
        return newBean;
    }
}
