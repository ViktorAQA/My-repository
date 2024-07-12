package com.project.demo.ui.base;


public class TestFrameworkException
                extends Exception
{
    private static final long serialVersionUID = 6700697376100822910L;


    /**
     * Constructs an <code>TestFrameworkException</code> with no detail  message.
     */
    public TestFrameworkException()
    {
        super();
    }


    /**
     * Constructs an <code>TestFrameworkException</code> with the specified detail message.
     *
     * @param s the detail message.
     */
    public TestFrameworkException(String s)
    {
        super(s);
    }


    /**
     * Constructs an <code>TestFrameworkException</code> with the specified detail message.
     *
     * @param message the detail message.
     * @param cause   the Throwable cause.
     */
    public TestFrameworkException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
