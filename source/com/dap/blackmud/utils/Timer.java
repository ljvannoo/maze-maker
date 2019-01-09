/******************************************************

Copyright © 2004 Northrop Grumman Corporation

All Rights Reserved

This material may be reproduced by or for the U.S.
Government pursuant to the copyright license under
the clause at Defense Federal Acquisition Regulation
Supplement (DFARS) 252.227-7014 (June 1995).

******************************************************/

/*
 * Timer.java
 *
 * Created on January 29, 2004, 11:21 AM
 */

/**
 *
 * @author  lukasv
 */
package com.dap.blackmud.utils;


public class Timer {
    String name;
    java.util.Date dt;
    long start,end;
    double dur;
    boolean running;

    /** Creates a new instance of Timer */
    public Timer(String newName) {
        name = newName;
        reset();
    }

    public void reset() {
        dt = null;
        start = 0;
        end = 0;
        dur = 0;
        running = false;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public void start() {
        dt = new java.util.Date();
        start = dt.getTime();
        running = true;
    }

    public double stop() {
        dt = new java.util.Date();
        end = dt.getTime();
        dur = (double)(end-start)/1000.0;
        running = false;
        return dur;
    }

    public double query() {
        if(end!=0 && start!=0) {
            dur = (double)(end-start)/1000.0;
            if(running) {
            } else {
            }
        } else {
            return 0;
        }
        return dur;
    }

    public double queryMilliseconds() {
        if(end!=0 && start!=0) {
            dur = (double)(end-start);
            if(running) {
            } else {
            }
        } else {
            return 0;
        }
        return dur;
    }

    public double querySeconds() {
        if(end!=0 && start!=0) {
            dur = (double)(end-start)/1000.0;
            if(running) {
            } else {
            }
        } else {
            return 0;
        }
        return dur;
    }

    public double queryMinutes() {
        if(end!=0 && start!=0) {
            dur = (double)((end-start)/1000.0)/60;
            if(running) {
            } else {
            }
        } else {
            return 0;
        }
        return dur;
    }
}
