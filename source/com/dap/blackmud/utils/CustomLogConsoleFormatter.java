//----------------------------------------------------------------------
//Copyright © 2004 Northrop Grumman Corporation -- All Rights Reserved
//
//This material may be reproduced by or for the U.S. Government pursuant
//to the copyright license under the clause at Defense Federal
//Acquisition Regulation Supplement (DFARS) 252.227-7014 (June 1995).
//----------------------------------------------------------------------

/*
 * Created on January 6, 2004, 10:58 AM
 */

/**
 * Provides custom log message formatting in the form of:
 * 'MMM dd yyyy hh:mm:ss :: level :: caller -- message'
 * 
 * @author  lukasv
 */
package com.dap.blackmud.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;

public class CustomLogConsoleFormatter extends Formatter {
    public static final int MAX_STRING_LENGTH = 1000;
    private boolean displayTags = true;
    public String format(java.util.logging.LogRecord record) {
        StringBuffer buf = new StringBuffer(MAX_STRING_LENGTH);
        SimpleDateFormat date = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
        String level = record.getLevel().toString()+"           ";
        level = level.substring(0,7);
        String logMessage = record.getMessage();
        String[] message = logMessage.split("\b");

        if(displayTags) {
            date.format(new Date(), buf, new java.text.FieldPosition(0));
            //buf.append("  "+record.getSourceClassName()+"."+record.getSourceMethodName()+"() - "+level+" - "+record.getMessage()+"\n");
            //buf.append("  "+message[0]+" - "+level+" -- "+message[1]+"\n");
            if(record.getLevel()==LogLevel.REPORT)
                buf.append("  ::"+level+":: "+message[1]+"\n");
            else
                buf.append("  ::"+level+":: "+message[0]+" -- "+message[1]+"\n");
        } else {
            buf.append(message[1]+"\n");
        }
        // *** Can't do this any more due to the new organization -LV
        //if(Global.debugWindow!=null) {
        //    Global.debugWindow.insertDebugText(record.getLevel(), buf.toString());
        //}
        if(record.getLevel() == LogLevel.WARNING || record.getLevel() == LogLevel.SEVERE)
            java.awt.Toolkit.getDefaultToolkit().beep();
        return buf.toString();
    }
    
    /**
     * Constructs a new CustomLogConsoleFormatter
     * @param displayTags Sets whether or not the formatter should display the date and orgin info
     * along with the log message
     */
    public CustomLogConsoleFormatter(boolean displayTags) {
        super();
        this.displayTags = displayTags;
    }
}
