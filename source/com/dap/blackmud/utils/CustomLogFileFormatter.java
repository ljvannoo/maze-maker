/******************************************************

Copyright © 2004 Northrop Grumman Corporation

All Rights Reserved

This material may be reproduced by or for the U.S.
Government pursuant to the copyright license under
the clause at Defense Federal Acquisition Regulation
Supplement (DFARS) 252.227-7014 (June 1995).

******************************************************/

/*
 * CustomLogFileFormatter.java
 *
 * Created on January 6, 2004, 11:09 AM
 */

/**
 *
 * @author  lukasv
 */
package com.dap.blackmud.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;

public class CustomLogFileFormatter extends Formatter {
    public static final int MAX_STRING_LENGTH = 1000;
    public String format(java.util.logging.LogRecord record) {
        StringBuffer buf = new StringBuffer(MAX_STRING_LENGTH);
        SimpleDateFormat date = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
        String level = record.getLevel().toString()+"           ";
        level = level.substring(0,7);
        String logMessage = record.getMessage();
        String[] message = logMessage.split("\b");

        date.format(new Date(), buf, new java.text.FieldPosition(0));
        //buf.append("  "+record.getSourceClassName()+"."+record.getSourceMethodName()+"() - "+level+" - "+record.getMessage()+"\n");
        //buf.append("  "+message[0]+" - "+level+" -- "+message[1]+"\n");
        if(record.getLevel()==LogLevel.REPORT)
            buf.append("  ::"+level+":: "+message[1]+"\n");
        else
            buf.append("  ::"+level+":: "+message[0]+" -- "+message[1]+"\n");

        return buf.toString();
    }
}
