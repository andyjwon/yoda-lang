package edu.lmu.cs.xlg.util;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A primitive logger. It logs (localized) messages to a print writer. Some
 * messages can be marked as error messages, and these are counted as they are
 * written. Clients can call <code>clearErrors</code> to set this count to zero
 * and <code>getErrorCount()</code> to get the number of errors logged since the
 * last call to <code>clearErrors()</code>.
 */
public class Log {

    private ResourceBundle bundle;
    private PrintWriter writer;
    private int errorCount = 0;
    private boolean quiet = false;

    /**
     * Constructs a <code>Log</code> object.
     *
     * @param resourceBundlePrefix
     *            the base name of the resource bundle, for example, if the
     *            prefix was "dog" then the resources would be taken from the
     *            file "dog.properties".
     * @param writer
     *            the writer to write the messages.
     */
    public Log(String resourceBundlePrefix, PrintWriter writer) {
        this.bundle = ResourceBundle.getBundle(resourceBundlePrefix);
        this.writer = writer;
    }

    /**
     * Resets the error count to zero. This method is provided so that a log
     * object can be reused.
     */
    public void clearErrors() {
        errorCount = 0;
    }

    /**
     * Returns the number of error messages written since the last call to
     * <code>clearErrors()</code>.
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * Tells this logger whether it should be quiet or not: in quiet mode, no
     * messages are written, although errors are still counted.
     *
     * @param quiet
     *            true if you don't want the messages written, false if you do.
     */
    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    /**
     * Logs a non-error message.
     *
     * @param key
     *            the key of the message in the resource bundle. If there is no
     *            such key in the bundle, the key will be used as the message
     *            and the arguments will be ignored.
     * @param arguments
     *            the variable part of the message.
     */
    public void message(String key, Object... arguments) {
        if (!quiet) {
            try {
                String message = bundle.getString(key);
                writer.println(MessageFormat.format(message, arguments));
            } catch (MissingResourceException e) {
                writer.println(key);
            }
        }
    }

    /**
     * Logs an error message.
     *
     * @param errorKey
     *            the resource bundle key of the error message. If there is no
     *            such key in the bundle, the key will be used as the error
     *            message.
     * @param arguments
     *            the variable parts of the error message.
     */
    public void error(String errorKey, Object... arguments) {
        errorCount++;
        message(errorKey, arguments);
    }

    /**
     * Logs a message from a Throwable. If the localized message of the
     * throwable happens to be a resource bundle key, we'll look it up from our
     * bundle. Otherwise we output the text of the localized message already
     * placed inside the exception. This is the best we can do for exceptions
     * thrown in third-party libraries.
     *
     * @param t
     *            the throwable whose localized message we want to log.
     */
    public void exception(Throwable t) {
        error(t.getLocalizedMessage());
    }
}
