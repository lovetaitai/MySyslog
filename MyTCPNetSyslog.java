package com.dbappsecurity;

import org.graylog2.syslog4j.SyslogRuntimeException;
import org.graylog2.syslog4j.impl.AbstractSyslogWriter;
import org.graylog2.syslog4j.impl.net.AbstractNetSyslog;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslog;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogConfigIF;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogWriter;

/**
 * TCPNetSyslog is an extension of AbstractSyslog that provides support for
 * TCP/IP-based syslog clients.
 * <p/>
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: TCPNetSyslog.java,v 1.21 2010/11/28 04:43:31 cvs Exp $
 */
public class MyTCPNetSyslog extends TCPNetSyslog {

    @Override
    public synchronized AbstractSyslogWriter getWriter(boolean create) {
        if (super.writer != null || !create) {
            return super.writer;
        }

        super.writer = (MyTCPNetSyslogWriter) createWriter();

        if (super.tcpNetSyslogConfig.isThreaded()) {
            createWriterThread(super.writer);
        }

        return super.writer;
    }

}
