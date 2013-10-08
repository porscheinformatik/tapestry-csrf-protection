package org.apache.tapestry5.csrfprotection;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation can be used to mark page classes. It is only relevant for the auto mode
 * of the cross-site-request forgery protection. If a page is marked as unprotected component or page
 * event requests are not checked for that page.
 */
@Target(value = {TYPE})
@Retention(RUNTIME)
@Documented
public @interface NotCsrfProtected
{
}
