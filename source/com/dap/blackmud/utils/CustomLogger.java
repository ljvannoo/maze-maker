//----------------------------------------------------------------------
//Copyright © 2004 Northrop Grumman Corporation -- All Rights Reserved
//
//This material may be reproduced by or for the U.S. Government pursuant
//to the copyright license under the clause at Defense Federal
//Acquisition Regulation Supplement (DFARS) 252.227-7014 (June 1995).
//----------------------------------------------------------------------

/*
 * Created on Mar 7, 2005
 */
package com.dap.blackmud.utils;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lukasv
 *
 * Provides a custome logging class that outputs log messages on a single line rather than
 * the default of two
 */
public class CustomLogger {
    boolean logLineNumbers = true;	// Whether or not to display line numbers
    Logger logger = null;	// The main logger
    
    /**
     * Creates a new CustomLogger
     * 
     * @param newLogger The base logger
     * @param logLineNumbers Whether or not to log line numbers
     */
    public CustomLogger(Logger newLogger, boolean logLineNumbers ) {
        logger = newLogger;
        this.logLineNumbers = logLineNumbers;
    }
    
    /**
     * Sets whether or not line numbers are logged
     * 
     * @param state If set to true, this logger will log line numbers
     */
    public void setLogLineNumbers(boolean state) {
        logLineNumbers = state;
    }
    
    public void setUseParentHandlers(boolean state) {
        logger.setUseParentHandlers(state);
    }
    
    public void setLevel(Level newLevel) {
        logger.setLevel(newLevel);
    }
    
    public void addHandler(Handler newHandler) {
        logger.addHandler(newHandler);
    }
    
    /**
     * Logs a message at the given level.  This method will also add prefix information used
     * by the custom handlers that are designed to split caller info from the message proper
     * 
     * @param level The level at which the message should be logged
     * @param msg The message to be logged
     */
    public void log(Level level, String msg) {
        try {
            throw new Throwable();
        } catch(Throwable e) {
            String[] stack = null;
            java.io.ByteArrayOutputStream bs = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(bs);
            e.printStackTrace(ps);
            ps.close();
            stack = bs.toString().split("\n");

            String message = null;
            if(logLineNumbers)
                message = stack[2].substring(4,stack[2].length()-1)+"\b"+msg;
            else
                message = stack[2].substring(4,stack[2].indexOf('('))+"()\b"+msg;
            logger.log(level, message);

        }
    }
}
