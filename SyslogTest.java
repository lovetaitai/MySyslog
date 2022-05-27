package com.dbappsecurity;

import com.sun.org.apache.regexp.internal.RE;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;
import org.graylog2.syslog4j.impl.net.AbstractNetSyslogConfig;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogConfigIF;
import org.graylog2.syslog4j.server.*;
import org.graylog2.syslog4j.server.impl.net.udp.UDPNetSyslogServerConfig;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.net.SocketAddress;

public class SyslogTest {



//    public static void main(String[] args) throws InterruptedException {
//        new SyslogTest().receiveSyslogMessage();
//    }

    public static void main(String[] args) {
        SyslogServerIF syslogServerIF = SyslogServer.getInstance("tcp");
//        UDPNetSyslogServerConfig config = new UDPNetSyslogServerConfig();
//
//
//
//        //config.setMaxMessageSize(56214400);
//        config.setPort(20514);
//        config.addEventHandler(new SyslogEvent());
        SyslogServerConfigIF configIF = syslogServerIF.getConfig();
        configIF.setPort(514);
        configIF.setHost("0.0.0.0");
        configIF.addEventHandler(new SyslogEvent());

        syslogServerIF.initialize("tcp", configIF);

        syslogServerIF.run();

    }


    private int pins(int n) {
        return n;
    }

    private int times(int n) {
        return n;
    }

    private void assertTrue(boolean b) {
    }




}
