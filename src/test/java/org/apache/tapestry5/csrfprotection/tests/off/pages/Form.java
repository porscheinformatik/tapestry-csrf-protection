package org.apache.tapestry5.csrfprotection.tests.off.pages;

import java.util.ArrayList;

import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

/**
 * Simple page with a form component that is tested in combination with the cross-site request forgery protection.
 */
public class Form
{
    @Property
    @Persist
    private String message;

    @Property
    @Persist
    private ArrayList<String> messages;

    @Log
    Object onSuccess()
    {
        messages.add(message);
        return this;
    }

    @Log
    void onActivate()
    {
        if (messages == null)
        {
            messages = new ArrayList<String>();
        }
    }
}
