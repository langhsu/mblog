package mblog.base.print;

/**
 * @author langhsu on 2015/9/6.
 */
public class Printer {
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("mblog");

    public static void info(String message) {
        log.info(message);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }
}
