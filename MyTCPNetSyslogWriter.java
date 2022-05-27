package com.dbappsecurity;

import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogRuntimeException;
import org.graylog2.syslog4j.impl.AbstractSyslog;
import org.graylog2.syslog4j.impl.AbstractSyslogWriter;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslog;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogConfigIF;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogWriter;
import org.graylog2.syslog4j.util.SyslogUtility;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * TCPNetSyslogWriter is an implementation of Runnable that supports sending
 * TCP-based messages within a separate Thread.
 * <p/>
 * <p>When used in "threaded" mode (see TCPNetSyslogConfig for the option),
 * a queuing mechanism is used (via LinkedList).</p>
 * <p/>
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: TCPNetSyslogWriter.java,v 1.20 2010/11/28 01:38:08 cvs Exp $
 */
public class MyTCPNetSyslogWriter extends TCPNetSyslogWriter {


    @Override
    public synchronized void shutdown() throws SyslogRuntimeException {
        super.shutdown = true;

        if (super.syslogConfig.isThreaded()) {
            long timeStart = System.currentTimeMillis();
            boolean done = false;

            while (!done) {
                if (super.socket == null || super.socket.isClosed()) {
                    done = true;

                } else {
                    long now = System.currentTimeMillis();

                    if (now > (timeStart + super.tcpNetSyslogConfig.getMaxShutdownWait())) {
                        // closeSocket(this.socket);
                        super.thread.interrupt();
                        done = true;
                    }

                    if (!done) {
                        SyslogUtility.sleep(SyslogConstants.SHUTDOWN_INTERVAL);
                    }
                }
            }

        } else {
            if (super.socket == null || super.socket.isClosed()) {
                return;
            }

            closeSocket(super.socket);
        }
    }

    @Override
    protected void runCompleted() {
        //closeSocket(this.socket);
    }
}
