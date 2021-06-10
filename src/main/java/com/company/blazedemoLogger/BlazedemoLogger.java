package com.company.blazedemoLogger;

import org.apache.log4j.Logger;

public class BlazedemoLogger {
        public static Logger logs = Logger.getLogger("ApplicationLogger");
        public static Logger SuccessMsgs = Logger.getLogger("SuccessMsgsLogger");
        public static Logger ErrorMsgs = Logger.getLogger("ErrorMsgsLogger");
        //logs
        private Logger logger = Logger.getLogger(this.getClass());

        public void logDebug(Object message) {
            if (logger.isDebugEnabled()) {
                logger.debug(message);
            }
        }

        public void logError(Object message, Throwable t) {
            logger.error(message, t);
        }

        public void logError(Object message) {
            logger.error(message);
        }
    }

