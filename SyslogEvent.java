package com.dbappsecurity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.graylog2.syslog4j.server.SyslogServerEventIF;
import org.graylog2.syslog4j.server.SyslogServerIF;
import org.graylog2.syslog4j.server.SyslogServerSessionlessEventHandlerIF;
import org.graylog2.syslog4j.server.impl.AbstractSyslogServer;
import org.graylog2.syslog4j.server.impl.net.tcp.TCPNetSyslogServer;
import org.graylog2.syslog4j.util.SyslogUtility;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SyslogEvent  implements SyslogServerSessionlessEventHandlerIF {

    private Long count = 0L;

    private Set ipSet = new HashSet();

    @Override
    public void event(SyslogServerIF syslogServerIF, SocketAddress socketAddress, SyslogServerEventIF syslogServerEventIF) {
        TCPNetSyslogServer tcpServer = (TCPNetSyslogServer) syslogServerIF;
        AbstractSyslogServer.Sessions session = tcpServer.getSessions();
        Iterator itr = session.getSockets();
        System.out.println("socket num:" + tcpServer.getSessions().size());
        String date = (syslogServerEventIF.getDate() == null ? new Date() : syslogServerEventIF.getDate()).toString();
        //将解析日志的生成端,<<3是要该数左移动三位计算
        String facility = SyslogUtility.getFacilityString(syslogServerEventIF.getFacility()<<3);
        //讲解析日志的级别，级别越大越低
        String level = SyslogUtility.getLevelString(syslogServerEventIF.getLevel());
        //获取当前的源设备IP
        String sourceIP = getIPAddress(socketAddress.toString());
        System.out.println(sourceIP);
        //获取到信息主体
        String msg = syslogServerEventIF.getMessage();
        //放入信息
        //msg = syslogServerEventIF.getMessage().replace(syslogServerEventIF.getHost(),"");

        //JSONObject obj = JSON.parseObject(msg);
        //String ip = obj.getString("addr");

        //ipSet.add(ip);
        
        System.out.println(msg);
            count++;
        System.out.println(count);
        //System.out.println(ipSet.size());
    }

    private String getIPAddress(String bString)
    {
        String regEx="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(bString);
        String result = "";
        while (m.find()) {
            result=m.group();

            break;
        }
        return result;
    }


    @Override
    public void exception(SyslogServerIF syslogServerIF, SocketAddress socketAddress, Exception e) {

    }

    @Override
    public void initialize(SyslogServerIF syslogServerIF) {

    }

    @Override
    public void destroy(SyslogServerIF syslogServerIF) {

    }
}
