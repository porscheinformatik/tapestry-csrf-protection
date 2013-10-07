package org.apache.tapestry5.csrfprotection.util;

/**
 * Simple bean used to test the BeanEditForm.
 */
public class TestBean
{
    private String testProperty = "";

    public TestBean()
    {
    }

    public TestBean(String testProperty)
    {
        this.testProperty = testProperty;
    }

    public void setTestProperty(String testProperty)
    {
        this.testProperty = testProperty;
    }

    public String getTestProperty()
    {
        return testProperty;
    }
}
