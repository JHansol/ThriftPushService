package com.naver.npns.server.controller;

import com.naver.npns.server.idl.PushReceiveService;
import com.naver.npns.server.service.ClientService;
import com.naver.npns.server.model.ConnectResult;
import com.naver.npns.server.model.Device;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectController {
    private static Logger log = LoggerFactory.getLogger(ConnectController.class);

    @Autowired
    private ClientService clientService;


    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/request_connect")
    public ConnectResult requestConnect(@RequestBody Device device) {
        log.warn("received device: {}", device);

        ConnectResult connectResult = new ConnectResult();
        boolean connectNewDeviceResult = connectNewDevice(device.getIp(), device.getUuid());

        if (!connectNewDeviceResult) {
            connectResult.setConnectResult("Fail To Connect New Device");
            return connectResult;
        }

        String pingResult = sendPingToDevice(device.getUuid());
        connectResult.setConnectResult(pingResult);

        log.warn("pingResult from ip {}: {}", device.getIp(), pingResult);
        return connectResult;
    }

    private String sendPingToDevice(String uuid) {

        String result = null;
        try {
            PushReceiveService.Client client = clientService.getClient(uuid);
            result = client.ping();
        } catch (TException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean connectNewDevice(String ip, String uuid) {

        boolean connectNewDeviceResult = false;

        try {
            TTransport transport = new TSocket(ip, 10000);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            PushReceiveService.Client client = new PushReceiveService.Client(protocol);
            clientService.putClient(uuid, client);
            connectNewDeviceResult = true;

        } catch (TTransportException e) {
            e.printStackTrace();
            log.debug("TTransportException {}", e);
        }

        return connectNewDeviceResult;

    }
}
