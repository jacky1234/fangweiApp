package com.jacky.sub.beedee.support.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Exceptions {
    // Don't let anyone instantiate this class.
    private Exceptions() {
        // This constructor is intentionally empty.
    }

    /**
     * Gets the stack trace from the Throwable as a string.
     *
     * @param throwable the <code>Throwable</code>
     * @return the string of the stack trace
     */
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
