import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Level;

public class ALog {

    @Log
    public static class Log1 {
        public void demo() {
            if (log.isLoggable(Level.INFO)) {
                log.info("고구마");
            }
        }
    }

    @Slf4j
    public static class Log2 {
        public void demo() {
            if (log.isInfoEnabled()) {
                log.info("{}", "고사리");
            }
        }
    }

    @CommonsLog(topic = "Log3")
    public static class Log3 {
        public void demo() {
            if (log.isInfoEnabled()) {
                log.info("고라니");
            }
        }
    }
}
