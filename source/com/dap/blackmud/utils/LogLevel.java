//----------------------------------------------------------------------
//Copyright © 2004 Northrop Grumman Corporation -- All Rights Reserved
//
//This material may be reproduced by or for the U.S. Government pursuant
//to the copyright license under the clause at Defense Federal
//Acquisition Regulation Supplement (DFARS) 252.227-7014 (June 1995).
//----------------------------------------------------------------------

/*
 * Created on Mar 4, 2005
 */

/**
 * Provides custom log levels.
 * 
 * @author  lukasv
 */
package com.dap.blackmud.utils;

import java.util.logging.Level;

public class LogLevel extends Level {

    private static final long serialVersionUID = 1L;
    public static final Level FATAL = new LogLevel("FATAL", Level.SEVERE.intValue()+30);
    public static final Level REPORT = new LogLevel("REPORT", Level.SEVERE.intValue()+60);
    public static final Level DEBUG = new LogLevel("DEBUG", Level.FINER.intValue()+60);
    public static final Level TIMER = new LogLevel("TIMER", Level.FINER.intValue()+45);
    public static final Level SQL = new LogLevel("SQL", Level.FINER.intValue()+30);
    /** Creates a new instance of DebugLevel */
    public LogLevel(String name, int value) {
        super(name,value);
    }
}
