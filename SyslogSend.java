package com.dbappsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConfigIF;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;
import org.graylog2.syslog4j.impl.AbstractSyslogWriter;
import org.graylog2.syslog4j.impl.net.AbstractNetSyslogConfig;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslog;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import org.graylog2.syslog4j.impl.net.tcp.TCPNetSyslogWriter;
import org.graylog2.syslog4j.server.*;

import java.net.SocketAddress;
import java.util.UUID;

public class SyslogSend {

    private static final String HOST = "10.20.28.159";
    private static final int PORT = 443;

    private void receiveSyslogMessage() throws InterruptedException {
        SyslogServerIF server = SyslogServer.getInstance(SyslogConstants.UDP);
        SyslogServerConfigIF config = server.getConfig();
        config.setHost(HOST);
        config.setPort(PORT);
        config.addEventHandler(new SyslogServerSessionEventHandlerIF() {
            @Override
            public Object sessionOpened(SyslogServerIF syslogServerIF, SocketAddress socketAddress) {
                return null;
            }

            @Override
            public void event(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress,
                              SyslogServerEventIF syslogServerEventIF) {
                System.out.println("receive from:" + socketAddress + "\tmessage" + syslogServerEventIF.getMessage());
            }

            @Override
            public void exception(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress, Exception e) {

            }

            @Override
            public void sessionClosed(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress, boolean b) {

            }

            @Override
            public void initialize(SyslogServerIF syslogServerIF) {

            }

            @Override
            public void destroy(SyslogServerIF syslogServerIF) {

            }
        });
        SyslogServer.getThreadedInstance(SyslogConstants.UDP);
        Thread.sleep(100000);
    }


    //    public static void main(String[] args) throws InterruptedException {
//        new SyslogTest().receiveSyslogMessage();
//    }
    public static void main(String[] args) throws JsonProcessingException {


        MyTCPNetSyslogConfig config = new MyTCPNetSyslogConfig("127.0.0.1", 514);
        config.setKeepAlive(true);
        config.setMaxShutdownWait(0);
        MyTCPNetSyslog syslogIF = new MyTCPNetSyslog();
        syslogIF.initialize("tcp", config);

        MyTCPNetSyslogWriter writer = (MyTCPNetSyslogWriter) syslogIF.getWriter();

        //待发送的消息内容
        MessageData messageData = new MessageData();
        messageData.setData_type(TypeEnum.BUSI_DATA.type);
        messageData.setData_source("business_data");
        Content cont = new Content();
        String code = UUID.randomUUID().toString();
        cont.setDeviceCode(code);
        cont.setOrgEquipId(code);
        cont.setEquipName("EQUIP_001");
        cont.setBrand("海康");
        cont.setDescription("模拟设备001");
        cont.setEquipmentInfo("模拟设备001： 描述信息");
        messageData.setData(cont);

        ObjectMapper objectMapper = new ObjectMapper();
        //发送消息
        writer.write(objectMapper.writeValueAsBytes(messageData));
        writer.flush();

        writer.write(objectMapper.writeValueAsBytes(messageData));
        writer.flush();

        writer.write(objectMapper.writeValueAsBytes(messageData));
        writer.flush();

        writer.write(objectMapper.writeValueAsBytes(messageData));
        writer.flush();

        writer.write(objectMapper.writeValueAsBytes(messageData));
        writer.flush();

    }

    static enum TypeEnum {
        //业务数据
        BUSI_DATA(5),
        //安全数据
        SECURITY_DATA(6);

        private final int type;

        TypeEnum(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class MessageData {
        private Integer data_type;
        private String data_source;
        private Content data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class Content {
        //资产编号
        private String deviceCode;
        //设备信息
        private String equipmentInfo;
        //设备品牌
        private String brand;
        //设备信息描述
        private String description;
        //设备名称
        private String equipName;
        //设备方设备id
        private String orgEquipId;

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
